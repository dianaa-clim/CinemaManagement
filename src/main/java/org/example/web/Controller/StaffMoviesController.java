package org.example.web.Controller;

import common.Account;
import common.Movie;
import jakarta.servlet.http.HttpSession;
import org.example.server.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/movies")
public class StaffMoviesController {

    private final MovieService movieService;

    public StaffMoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    // LISTA FILME
    @GetMapping
    public String manageMovies(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("user");
        if (user == null || !"Employee".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("movies", movieService.findAll());
        return "staff/movies";
    }

    // PAGINA ADD MOVIE
    @GetMapping("/add")
    public String addMoviePage(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("user");
        if (user == null || !"Employee".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("movie", new Movie());
        return "staff/add-movie";
    }

    // SAVE MOVIE
    @PostMapping("/add")
    public String saveMovie(@ModelAttribute Movie movie, HttpSession session) {
        Account user = (Account) session.getAttribute("user");
        if (user == null || !"Employee".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        movieService.addMovie(movie);
        return "redirect:/staff/movies";
    }
}
