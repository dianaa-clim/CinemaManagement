package org.example.web.store;

import java.time.LocalDate;
import java.util.*;

public class CinemaStore {

    // ========== MODELE ==========
    public record Movie(
            String id,
            String title,
            String genre,
            String duration,
            String rating,
            LocalDate premiereDate,
            LocalDate runFrom,
            LocalDate runTo,
            String description,
            String imageUrl,
            int basePriceLei
    ) {}

    public record Show(
            String id,          // showId
            String movieId,
            LocalDate date,
            String time,        // "18:10"
            int roomNumber,
            String format,      // "2D", "IMAX"
            String language     // "RO", "EN"
    ) {}

    // ========== DATE DUMMY (MUTABILE / DB-READY) ==========
    // ✅ Map mutabil: add/delete movies ușor
    public static final Map<String, Movie> MOVIES = new LinkedHashMap<>();

    // ✅ show-uri mutabile
    public static final List<Show> SHOWS = new ArrayList<>();

    // ✅ locuri ocupate per showId
    public static final Map<String, Set<String>> OCCUPIED_BY_SHOW_ID = new HashMap<>();

    static {
        // date de exemplu
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        // ===== filme =====
        addMovie(new Movie(
                "dune2",
                "Dune: Part Two",
                "Sci-Fi",
                "166 min",
                "AP-12",
                LocalDate.of(2026, 1, 2),
                today.minusDays(5),          // runFrom (ex: a început deja)
                today.plusDays(20),          // runTo
                "A continuation of Paul Atreides’ journey...",
                "/img/posters/dune2.jpg",
                35
        ));

        addMovie(new Movie(
                "tara",
                "Dragoste la țară",
                "Comedie",
                "80 min",
                "AP-12",
                LocalDate.of(2026, 1, 2),
                today.minusDays(2),
                today.plusDays(10),
                "Ana, fata de oraș, ajunge la țară și descoperă...",
                "/img/posters/tara.jpg",
                28
        ));

        // ===== show-uri =====
        // Dune
        addShow("S1", "dune2", today, "18:10", 1, "2D", "EN");
        addShow("S2", "dune2", today, "20:30", 2, "IMAX", "EN");
        addShow("S3", "dune2", tomorrow, "19:00", 3, "2D", "EN");

        // Tara
        addShow("S4", "tara", today, "17:20", 1, "2D", "RO");
        addShow("S5", "tara", today, "19:40", 4, "2D", "RO");
        addShow("S6", "tara", tomorrow, "18:00", 5, "2D", "RO");

        // ===== ocupate dummy =====
        OCCUPIED_BY_SHOW_ID.put("S1", new HashSet<>(Set.of("A1", "A2", "B5", "C7")));
        OCCUPIED_BY_SHOW_ID.put("S3", new HashSet<>(Set.of("D4", "D5", "E6")));
        OCCUPIED_BY_SHOW_ID.put("S4", new HashSet<>(Set.of("A10", "B10", "C10")));
    }

    // ========== MOVIE CRUD ==========
    public static void addMovie(Movie movie) {
        MOVIES.put(movie.id(), movie);
    }

    public static void deleteMovie(String movieId) {
        MOVIES.remove(movieId);

        // ștergem și show-urile asociate filmului
        SHOWS.removeIf(s -> s.movieId().equals(movieId));

        // curățăm și map-ul de ocupate pentru show-urile șterse
        // (sigur, chiar dacă showId-urile nu mai există)
        // — nu știm ce showId-uri au fost șterse, așa că curățăm orice care nu mai e în SHOWS
        Set<String> existingShowIds = new HashSet<>();
        for (Show s : SHOWS) existingShowIds.add(s.id());
        OCCUPIED_BY_SHOW_ID.keySet().removeIf(id -> !existingShowIds.contains(id));
    }

    public static Collection<Movie> allMovies() {
        return MOVIES.values();
    }

    // ========== SHOW HELPERS ==========
    public static void addShow(String id, String movieId, LocalDate date, String time,
                               int room, String format, String language) {
        SHOWS.add(new Show(id, movieId, date, time, room, format, language));
    }

    // ========== HELPERS ==========
    public static Movie getMovie(String id) {
        Movie m = MOVIES.get(id);
        if (m == null) throw new IllegalArgumentException("Movie not found: " + id);
        return m;
    }

    public static Show getShow(String showId) {
        return SHOWS.stream().filter(s -> s.id().equals(showId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + showId));
    }

    public static List<Show> getShowsForMovieAndDate(String movieId, LocalDate date) {
        return SHOWS.stream()
                .filter(s -> s.movieId().equals(movieId) && s.date().equals(date))
                .sorted(Comparator.comparing(CinemaStore.Show::time))
                .toList();
    }

    // ✅ folosit în /movies (listă) ca să construim lista completă pe o zi
    public static List<Movie> getMoviesPlayingOn(LocalDate date) {
        return MOVIES.values().stream()
                .filter(m -> (m.runFrom() == null || !date.isBefore(m.runFrom())) &&
                        (m.runTo() == null || !date.isAfter(m.runTo())))
                .toList();
    }

    public static Set<String> getOccupiedSeats(String showId) {
        return OCCUPIED_BY_SHOW_ID.getOrDefault(showId, Set.of());
    }
}
