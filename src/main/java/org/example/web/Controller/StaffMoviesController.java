package org.example.web.Controller;

import org.example.web.store.CinemaStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/staff/movies")
public class StaffMoviesController {

    @GetMapping
    public String movies(Model model) {
        model.addAttribute("movies", CinemaStore.allMovies());
        return "staff/movies";
    }

    @GetMapping("/new")
    public String newMovieForm() {
        return "staff/movie_new";
    }

    @PostMapping("/new")
    public String createMovie(@RequestParam String title,
                              @RequestParam String genre,
                              @RequestParam String rating,
                              @RequestParam String duration,
                              @RequestParam LocalDate premiereDate,
                              @RequestParam String description,
                              @RequestParam String imageUrl,
                              @RequestParam int basePriceLei,
                              @RequestParam LocalDate runFrom,
                              @RequestParam LocalDate runTo) {

        String id = UUID.randomUUID().toString().substring(0, 8);

        CinemaStore.addMovie(new CinemaStore.Movie(
                id,
                title,
                genre,
                duration,
                rating,
                premiereDate,
                runFrom,
                runTo,
                description,
                imageUrl,
                basePriceLei
        ));

        return "redirect:/staff/movies";
    }

    @PostMapping("/{id}/delete")
    public String deleteMovie(@PathVariable String id) {
        CinemaStore.deleteMovie(id);
        return "redirect:/staff/movies";
    }
}

