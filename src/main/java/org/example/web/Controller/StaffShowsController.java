package org.example.web.Controller;

import org.example.web.store.CinemaStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/staff/shows")
public class StaffShowsController {

    @GetMapping
    public String shows(Model model) {
        model.addAttribute("shows", CinemaStore.SHOWS);
        model.addAttribute("moviesById", CinemaStore.MOVIES);
        return "staff/shows";
    }

    @GetMapping("/new")
    public String newShowForm(Model model) {
        model.addAttribute("movies", CinemaStore.allMovies());
        return "staff/show_new";
    }

    @PostMapping("/new")
    public String createShow(@RequestParam String movieId,
                             @RequestParam LocalDate date,
                             @RequestParam String time,
                             @RequestParam int roomNumber,
                             @RequestParam String format,
                             @RequestParam String language) {

        String showId = UUID.randomUUID().toString().substring(0, 8);

        CinemaStore.addShow(
                showId,
                movieId,
                date,
                time,
                roomNumber,
                format,
                language
        );

        return "redirect:/staff/shows";
    }

    @PostMapping("/{id}/delete")
    public String deleteShow(@PathVariable String id) {
        CinemaStore.SHOWS.removeIf(s -> s.id().equals(id));
        CinemaStore.OCCUPIED_BY_SHOW_ID.remove(id);
        return "redirect:/staff/shows";
    }
}
