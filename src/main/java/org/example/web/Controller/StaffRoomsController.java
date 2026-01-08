package org.example.web.Controller;

import org.example.web.store.CinemaStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/staff/rooms")
public class StaffRoomsController {

    @GetMapping
    public String selectShow(Model model) {
        model.addAttribute("shows", CinemaStore.SHOWS);
        model.addAttribute("moviesById", CinemaStore.MOVIES);
        return "staff/rooms";
    }

    @GetMapping("/{showId}")
    public String room(@PathVariable String showId, Model model) {

        var show = CinemaStore.getShow(showId);
        var movie = CinemaStore.getMovie(show.movieId());

        Set<String> occupied =
                new HashSet<>(CinemaStore.getOccupiedSeats(showId));

        model.addAttribute("show", show);
        model.addAttribute("movie", movie);
        model.addAttribute("occupiedSeats", occupied);

        model.addAttribute("rows", List.of("A","B","C","D","E"));
        model.addAttribute("cols", List.of(1,2,3,4,5,6,7,8,9,10));

        return "staff/room_view";
    }

    @PostMapping("/{showId}/toggle")
    public String toggleSeat(@PathVariable String showId,
                             @RequestParam String seat) {

        CinemaStore.OCCUPIED_BY_SHOW_ID
                .computeIfAbsent(showId, k -> new HashSet<>());

        Set<String> occupied = CinemaStore.OCCUPIED_BY_SHOW_ID.get(showId);

        if (occupied.contains(seat)) {
            occupied.remove(seat);   // eliberare
        } else {
            occupied.add(seat);      // ocupare walk-in
        }

        return "redirect:/staff/rooms/" + showId;
    }
}

