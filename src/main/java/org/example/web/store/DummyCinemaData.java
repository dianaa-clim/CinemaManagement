package org.example.web.store;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

/**
 * Dummy, in-memory data source for the web UI.
 * Later you can replace this with services/repos backed by DB.
 */
@Component
public class DummyCinemaData {

    public record Showtime(String time, String format, String language, int roomNumber) {}

    public record Movie(
            String id,
            String title,
            String rating,
            String genre,
            String duration,
            String imageUrl,
            String description,
            LocalDate premiereDate,
            int basePriceLei
    ) {}

    private final List<Movie> movies;

    public DummyCinemaData() {
        movies = List.of(
                new Movie(
                        "dune2",
                        "Dune: Part Two",
                        "AP-12",
                        "Sci-Fi",
                        "166 min",
                        "/img/posters/dune2.jpg",
                        "Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family.",
                        LocalDate.of(2026, 1, 2),
                        30
                ),
                new Movie(
                        "oppenheimer",
                        "Oppenheimer",
                        "AP-12",
                        "Drama",
                        "180 min",
                        "/img/posters/oppenheimer.jpg",
                        "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.",
                        LocalDate.of(2026, 1, 10),
                        28
                ),
                new Movie(
                        "insideout2",
                        "Inside Out 2",
                        "AG",
                        "Animation",
                        "96 min",
                        "/img/posters/insideout2.jpg",
                        "Riley enters her teenage years and new emotions arrive at headquarters.",
                        LocalDate.of(2026, 1, 5),
                        25
                )
        );
    }

    public List<Movie> allMovies() {
        return movies;
    }

    public Optional<Movie> findMovie(String id) {
        return movies.stream().filter(m -> m.id().equals(id)).findFirst();
    }

    /**
     * Dummy showtimes per movie. You can easily extend this later.
     */
    public List<Showtime> showtimesFor(String movieId, LocalDate date) {
        // For now the date doesn't change the schedule; we keep it to match your future design.
        return switch (movieId) {
            case "dune2" -> List.of(
                    new Showtime("18:10", "2D", "EN (SUB RO)", 1),
                    new Showtime("20:40", "IMAX", "EN (SUB RO)", 3),
                    new Showtime("22:30", "2D", "EN (SUB RO)", 2)
            );
            case "oppenheimer" -> List.of(
                    new Showtime("17:30", "2D", "EN (SUB RO)", 4),
                    new Showtime("20:15", "2D", "EN (SUB RO)", 4)
            );
            case "insideout2" -> List.of(
                    new Showtime("16:20", "2D", "RO", 5),
                    new Showtime("18:00", "2D", "RO", 5),
                    new Showtime("19:40", "3D", "RO", 2)
            );
            default -> List.of();
        };
    }

    public int roomNumberFor(String movieId, LocalDate date, String time) {
        return showtimesFor(movieId, date).stream()
                .filter(s -> s.time().equals(time))
                .findFirst()
                .map(Showtime::roomNumber)
                .orElse(1);
    }
}

