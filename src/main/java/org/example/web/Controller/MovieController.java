package org.example.web.Controller;

import org.example.web.store.CinemaStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MovieController {

    public record ShowView(String id, String time, String format, String language, int roomNumber) {}

    public record MovieListItemView(
            String id,
            String title,
            String rating,
            String genre,
            String duration,
            String imageUrl,
            int priceLei,
            List<ShowView> showtimes
    ) {}

    @GetMapping("/movies")
    public String movies(@RequestParam(name = "date", required = false) String dateStr,
                         @RequestParam(name = "q", required = false) String q,
                         Model model) {

        LocalDate date = (dateStr == null || dateStr.isBlank()) ? LocalDate.now() : LocalDate.parse(dateStr);
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedDateLabel", date.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy")));

        String query = (q == null) ? "" : q.trim();
        model.addAttribute("q", query);

        List<MovieListItemView> movies = CinemaStore.MOVIES.values().stream()
                .filter(m -> query.isBlank() || m.title().toLowerCase().contains(query.toLowerCase()))
                .map(m -> new MovieListItemView(
                        m.id(),
                        m.title(),
                        m.rating(),
                        m.genre(),
                        m.duration(),
                        m.imageUrl(),
                        m.basePriceLei(),
                        CinemaStore.getShowsForMovieAndDate(m.id(), date).stream()
                                .map(s -> new ShowView(s.id(), s.time(), s.format(), s.language(), s.roomNumber()))
                                .toList()
                ))
                .toList();

        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/movies/{id}")
    public String movieDetails(@PathVariable String id,
                               @RequestParam(name = "date", required = false) String dateStr,
                               Model model) {

        LocalDate selectedDate = (dateStr == null || dateStr.isBlank())
                ? LocalDate.now()
                : LocalDate.parse(dateStr);

        var movie = CinemaStore.getMovie(id);
        var shows = CinemaStore.getShowsForMovieAndDate(id, selectedDate);

        model.addAttribute("movie", movie);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("shows", shows);

        return "movie-details";
    }
}
