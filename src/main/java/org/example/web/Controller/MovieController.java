package org.example.web.Controller;

import common.Movie;
import common.Show;
import org.example.server.service.MovieService;
import org.example.server.service.ShowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.example.server.service.ReviewService;

import java.time.LocalDate;
import java.util.*;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final ShowService showService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService, ShowService showService, ReviewService reviewService) {
        this.movieService = movieService;
        this.showService = showService;
        this.reviewService = reviewService;
    }


//    @GetMapping("/movies")
//    public String movies(Model model) {
//        model.addAttribute("movies", movieService.findAll());
//        return "movies";
//    }

//    @GetMapping("/movies/{id}")
//    public String movieDetails(@PathVariable int id, Model model) {
//        Movie movie = movieService.findById(id);
//        if (movie == null) return "redirect:/movies";
//
//        // showtimes grupate pe zile
//        Map<LocalDate, List<Show>> showsByDate = new TreeMap<>();
//        for (Show s : showService.findAll()) {
//            if (s.getMovieId() == id) {
//                showsByDate.computeIfAbsent(s.getDate(), k -> new ArrayList<>()).add(s);
//            }
//        }
//        for (var e : showsByDate.entrySet()) {
//            e.getValue().sort(Comparator.comparing(Show::getTime));
//        }
//
//        model.addAttribute("movie", movie);
//        model.addAttribute("showsByDate", showsByDate);
//
//        return "movie-details";
//    }


    @GetMapping("/movies/{id}")
    public String movieDetails(@PathVariable int id, Model model, HttpSession session) {
        Movie movie = movieService.findById(id);
        if (movie == null) return "redirect:/movies";

        Map<LocalDate, List<Show>> showsByDate = new TreeMap<>();
        for (Show s : showService.findAll()) {
            if (s.getMovieId() == id) {
                showsByDate.computeIfAbsent(s.getDate(), k -> new ArrayList<>()).add(s);
            }
        }
        for (var e : showsByDate.entrySet()) {
            e.getValue().sort(Comparator.comparing(Show::getTime));
        }

        model.addAttribute("movie", movie);
        model.addAttribute("showsByDate", showsByDate);

        // ✅ AICI e corect să pui review-urile (id = idFilm)
        model.addAttribute("reviews", reviewService.getReviewsForFilm(id));
        model.addAttribute("avgRating", reviewService.getAverageRating(id));
        model.addAttribute("canReview", session.getAttribute("user") != null);

        return "movie-details";
    }

    //    @GetMapping("/movies")
//    public String movies(
//            @RequestParam(name = "search", required = false) String search,
//            Model model
//    ) {
//        List<Movie> movies = (search == null || search.isBlank())
//                ? movieService.findAll()
//                : movieService.searchByTitle(search);
//
//        model.addAttribute("movies", movies);
//        model.addAttribute("search", search); // ca să păstrezi textul în input
//        return "movies";
//    }
@GetMapping("/movies")
public String movies(
        @RequestParam(name = "date", required = false) String dateStr,
        @RequestParam(name = "search", required = false) String search,
        @RequestParam(name = "genre", required = false) String genre,
        Model model
) {
    LocalDate date = (dateStr == null || dateStr.isBlank())
            ? LocalDate.now()
            : LocalDate.parse(dateStr);

    List<Movie> movies = movieService.findFiltered(search, genre);

    model.addAttribute("selectedDate", date);
    model.addAttribute("movies", movies);

    model.addAttribute("search", search);
    model.addAttribute("genre", genre);



    model.addAttribute("genres", movieService.getAllGenres()); // pentru dropdown

    return "movies";
}



}
