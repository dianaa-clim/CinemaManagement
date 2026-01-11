package org.example.web.Controller;

import common.Account;
import common.Movie;
import common.Reservation;
import common.Show;
import jakarta.servlet.http.HttpSession;
import org.example.server.dao.ReservationDAO;
import org.example.server.service.MovieService;
import org.example.server.service.ShowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

@Controller
public class BookingController {

    public record ShowView(
            int idShow,
            LocalDate date,
            LocalTime time,
            int roomNumber,
            String format,
            String language
    ) {}

    private final ReservationDAO reservationDAO;
    private final ShowService showService;
    private final MovieService movieService;

    public BookingController(ReservationDAO reservationDAO,
                             ShowService showService,
                             MovieService movieService) {
        this.reservationDAO = reservationDAO;
        this.showService = showService;
        this.movieService = movieService;
    }

    // ================= GET booking =================

    @GetMapping("/booking")
    public String booking(@RequestParam("showId") int showId,
                          HttpSession session,
                          Model model) {

        Account user = (Account) session.getAttribute("user"); // poate fi null

        Show show = showService.findById(showId);
        if (show == null) return "redirect:/movies";

        Movie movie = movieService.findById(show.getMovieId());
        if (movie == null) return "redirect:/movies";

        // ✅ găsim movieScheduleId corect pentru show (film + date + time)
        Integer movieScheduleId = reservationDAO.findMovieScheduleIdForShow(
                show.getMovieId(),
                show.getDate(),
                show.getTime()
        );

        // locuri ocupate din DB (DOAR dacă avem scheduleId valid)
        List<Integer> occupiedSeatIds = (movieScheduleId == null)
                ? List.of()
                : reservationDAO.findOccupiedSeatIdsByMovieSchedule(movieScheduleId);

        // convertim în "A1", "B5" pentru template
        Set<String> occupiedSeats = new HashSet<>();
        for (int idSeat : occupiedSeatIds) {
            String code = idSeatToSeatCode(idSeat);
            if (!"?".equals(code)) occupiedSeats.add(code);
        }

        List<String> rows = List.of("A", "B", "C", "D", "E", "F");
        List<String> cols = IntStream.rangeClosed(1, 10)
                .mapToObj(String::valueOf)
                .toList();

        model.addAttribute("movie", movie);
        model.addAttribute("show", toShowView(show));
        model.addAttribute("showId", showId);

        // ✅ important: trimitem movieScheduleId în view ca hidden input
        model.addAttribute("movieScheduleId", movieScheduleId);

        model.addAttribute("occupiedSeats", occupiedSeats);
        model.addAttribute("rows", rows);
        model.addAttribute("cols", cols);

        // dacă nu există schedule, poți afișa un mesaj (opțional)
        model.addAttribute("noSchedule", movieScheduleId == null);

        return "booking";
    }

    // ================= POST confirm =================

    @PostMapping("/booking/confirm")
    public String confirm(@RequestParam("showId") int showId,
                          @RequestParam("movieScheduleId") Integer movieScheduleId,
                          @RequestParam(required = false) List<String> seats,
                          HttpSession session,
                          Model model) {

        System.out.println(">>> HIT /booking/confirm showId=" + showId
                + " movieScheduleId=" + movieScheduleId
                + " seats=" + seats);

        Account user = (Account) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (seats == null || seats.isEmpty()) {
            return "redirect:/booking?showId=" + showId;
        }

        if (movieScheduleId == null) {
            // nu avem schedule => nu putem salva rezervare
            return "redirect:/booking?showId=" + showId;
        }

        Show show = showService.findById(showId);
        if (show == null) return "redirect:/movies";

        Movie movie = movieService.findById(show.getMovieId());
        if (movie == null) return "redirect:/movies";

        int accountId = user.getIdAccount();

        // ✅ FK-ul tău cere client.id_client (nu account)
        Integer clientId = reservationDAO.findClientIdByAccountId(accountId);
        if (clientId == null) {
            // contul nu are profil de client în tabela client
            return "redirect:/register";
        }

        // ✅ ocupate pentru schedule-ul corect
        Set<Integer> occupied = new HashSet<>(reservationDAO.findOccupiedSeatIdsByMovieSchedule(movieScheduleId));

        LinkedHashSet<String> uniqueSeats = new LinkedHashSet<>(seats);

        List<String> savedSeats = new ArrayList<>();
        boolean taken = false;

        // poți păstra 1 dacă nu ai tabel de seats / nu ai sală per show în cod
        // (nu afectează găsirea movieScheduleId, fiindcă asta vine deja din hidden)
        int roomId = 1;

        for (String seatCode : uniqueSeats) {
            Integer idSeat = seatCodeToIdSeat(seatCode);
            if (idSeat == null) continue;

            if (occupied.contains(idSeat)) {
                taken = true;
                continue;
            }

            Reservation r = new Reservation(
                    0,
                    clientId,
                    show.getMovieId(),
                    roomId,
                    idSeat,
                    LocalDate.now(),
                    0f,
                    movieScheduleId
            );

            reservationDAO.save(r);

            savedSeats.add(seatCode);
            occupied.add(idSeat);
        }

        if (savedSeats.isEmpty()) {
            return "redirect:/booking?showId=" + showId;
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("movie", movie);
        model.addAttribute("show", toShowView(show));
        model.addAttribute("seats", savedSeats);
        model.addAttribute("taken", taken);

        return "booking_success";
    }

    // ================= helpers =================

    private ShowView toShowView(Show show) {
        return new ShowView(
                show.getIdShow(),
                show.getDate(),
                show.getTime(),
                1,
                "2D",
                "RO"
        );
    }

    // A1=1, A10=10, B1=11 ... F10=60
    private Integer seatCodeToIdSeat(String seatCode) {
        if (seatCode == null || seatCode.length() < 2) return null;

        char row = Character.toUpperCase(seatCode.charAt(0));
        int col;

        try {
            col = Integer.parseInt(seatCode.substring(1));
        } catch (NumberFormatException e) {
            return null;
        }

        if (row < 'A' || row > 'F' || col < 1 || col > 10) return null;

        return (row - 'A') * 10 + col;
    }

    private String idSeatToSeatCode(int idSeat) {
        if (idSeat < 1 || idSeat > 60) return "?";
        char row = (char) ('A' + (idSeat - 1) / 10);
        int col = ((idSeat - 1) % 10) + 1;
        return row + String.valueOf(col);
    }
}
