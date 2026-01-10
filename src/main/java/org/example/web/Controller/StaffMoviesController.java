package org.example.web.Controller;

import common.Movie;
import org.example.server.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/staff/movies")
public class StaffMoviesController {

    private final MovieService movieService;

    public StaffMoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    // ← ASTA LIPSEA (manage films)
    @GetMapping
    public String staffMovies(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "staff/movies";
    }

    @GetMapping("/new")
    public String newMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "staff/movie_new";
    }

    @PostMapping("/new")
    public String saveMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/staff/movies";
    }
}
