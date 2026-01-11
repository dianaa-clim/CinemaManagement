package org.example.web.Controller;

import common.Movie;
import org.example.server.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    private final MovieService movieService;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<Movie> all = movieService.findAll();
        LocalDate today = LocalDate.now();

        // "Acum in cinema": azi intre runFrom si runTo (daca sunt null, le consideram OK)
        List<Movie> nowPlaying = all.stream()
                .filter(m -> m.getRunFrom() == null || !today.isBefore(m.getRunFrom()))
                .filter(m -> m.getRunTo() == null || !today.isAfter(m.getRunTo()))
                .toList();

        List<Movie> upcoming = all.stream()
                .filter(m -> m.getRunFrom() != null)          // are data de start
                .filter(m -> today.isBefore(m.getRunFrom()))  // incepe in viitor
                .toList();


        Movie featured = !nowPlaying.isEmpty() ? nowPlaying.get(0) : (all.isEmpty() ? null : all.get(0));

        model.addAttribute("featuredMovie", featured);
        model.addAttribute("moviesNow", nowPlaying);
        model.addAttribute("moviesUpcoming", upcoming.stream().limit(8).toList());


        return "index";
    }
}

