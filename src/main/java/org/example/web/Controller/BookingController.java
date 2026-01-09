package org.example.web.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.web.store.CinemaStore;
import org.example.web.store.ReservationStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class BookingController {

    private final ReservationStore reservationStore;

    public BookingController(ReservationStore reservationStore) {
        this.reservationStore = reservationStore;
    }

    /* ===================== SELECT SEATS ===================== */

    @GetMapping("/booking")
    public String booking(@RequestParam("showId") String showId,
                          HttpSession session,
                          Model model) {

        Integer accountId = (Integer) session.getAttribute("accountId");
        if (accountId == null) {
            return "redirect:/login";
        }

        var show = CinemaStore.getShow(showId);
        var movie = CinemaStore.getMovie(show.movieId());

        // OCCUPIED_BY_SHOW_ID este Map<String, Set<String>>
        List<String> occupiedSeats = new ArrayList<>(
                CinemaStore.OCCUPIED_BY_SHOW_ID
                        .getOrDefault(showId, Set.of())
        );

        model.addAttribute("movie", movie);
        model.addAttribute("show", show);
        model.addAttribute("occupiedSeats", occupiedSeats);

        return "booking";
    }

    /* ===================== CONFIRM BOOKING ===================== */

    @PostMapping("/booking/confirm")
    public String confirm(@RequestParam("showId") String showId,
                          @RequestParam(required = false) List<String> seats,
                          HttpSession session,
                          Model model) {

        Integer accountId = (Integer) session.getAttribute("accountId");
        if (accountId == null) {
            return "redirect:/login";
        }

        if (seats == null || seats.isEmpty()) {
            return "redirect:/booking?showId=" + showId;
        }

        var show = CinemaStore.getShow(showId);
        var movie = CinemaStore.getMovie(show.movieId());

        // creează rezervarea
        var reservation = reservationStore.create(accountId, showId, seats);

        // marchează locurile ca ocupate (CORECT: Set<String>)
        CinemaStore.OCCUPIED_BY_SHOW_ID
                .computeIfAbsent(showId, k -> new HashSet<>())
                .addAll(seats);

        model.addAttribute("movie", movie);
        model.addAttribute("show", show);
        model.addAttribute("seats", seats);
        model.addAttribute("reservation", reservation);

        return "booking-confirm";
    }
}
