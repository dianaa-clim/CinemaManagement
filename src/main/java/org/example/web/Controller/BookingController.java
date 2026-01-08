package org.example.web.Controller;

import org.example.web.store.CinemaStore;
import org.example.web.store.ReservationStore;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class BookingController {

    private final ReservationStore reservationStore;

    public BookingController(ReservationStore reservationStore) {
        this.reservationStore = reservationStore;
    }

    @GetMapping("/booking")
    public String booking(@RequestParam(name = "showId", required = false) String showId,
                          Model model,
                          Principal principal) {

        // Dacă cineva ajunge pe /booking fără showId (refresh / link vechi), nu mai dăm 400
        if (showId == null || showId.isBlank()) {
            return "redirect:/movies";
        }

        var show = CinemaStore.getShow(showId);
        var movie = CinemaStore.getMovie(show.movieId());
        var occupied = CinemaStore.getOccupiedSeats(showId);

        model.addAttribute("showId", showId);
        model.addAttribute("show", show);
        model.addAttribute("movie", movie);
        model.addAttribute("occupiedSeats", occupied);

        // layout dummy (în DB va veni din Room)
        model.addAttribute("rows", List.of("A","B","C","D","E"));
        model.addAttribute("cols", List.of(1,2,3,4,5,6,7,8,9,10));

        return "booking";
    }

    @PostMapping("/booking/confirm")
    public String confirm(@RequestParam("showId") String showId,
                          @RequestParam(required = false) List<String> seats,
                          Authentication auth,
                          Model model) {

        if (seats == null || seats.isEmpty()) {
            return "redirect:/booking?showId=" + showId;
        }

        var show = CinemaStore.getShow(showId);
        var movie = CinemaStore.getMovie(show.movieId());

        // creează rezervare
        var reservation = reservationStore.create(auth.getName(), showId, seats);

        // marchează locurile ca ocupate (ca să se vadă imediat)
        CinemaStore.OCCUPIED_BY_SHOW_ID
                .computeIfAbsent(showId, k -> new java.util.HashSet<>())
                .addAll(seats);

        model.addAttribute("reservation", reservation);
        model.addAttribute("show", show);
        model.addAttribute("movie", movie);

        return "booking_success";
    }

}
