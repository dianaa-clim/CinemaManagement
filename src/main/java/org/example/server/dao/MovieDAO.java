package org.example.server.dao;

import common.Movie;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


@Repository
public class MovieDAO {

    private final Connection connection;

    public MovieDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();

        String sql = """
        SELECT id_film, title, duration, genre, description,
               rating, base_price, image_url,
               run_from, run_to, premiere_date
        FROM film
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Movie m = new Movie(
                        rs.getInt("id_film"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("genre"),
                        rs.getString("description"),
                        rs.getBigDecimal("rating"),
                        rs.getBigDecimal("base_price"),
                        rs.getString("image_url"),
                        getLocalDate(rs, "run_from"),
                        getLocalDate(rs, "run_to"),
                        getLocalDate(rs, "premiere_date")
                );
                movies.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }


    public void insert(Movie movie) {

        String sql = """
        INSERT INTO film
        (title, duration, genre, description, rating, base_price,
         image_url, run_from, run_to, premiere_date)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getDuration());
            stmt.setString(3, movie.getGenre());
            stmt.setString(4, movie.getDescription());
            stmt.setBigDecimal(5, movie.getRating());
            stmt.setBigDecimal(6, movie.getBasePrice());
            stmt.setString(7, movie.getImageUrl());

            // run_from
            if (movie.getRunFrom() != null) {
                stmt.setDate(8, java.sql.Date.valueOf(movie.getRunFrom()));
            } else {
                stmt.setNull(8, java.sql.Types.DATE);
            }

            // run_to
            if (movie.getRunTo() != null) {
                stmt.setDate(9, java.sql.Date.valueOf(movie.getRunTo()));
            } else {
                stmt.setNull(9, java.sql.Types.DATE);
            }

            // premiere_date
            if (movie.getPremiereDate() != null) {
                stmt.setDate(10, java.sql.Date.valueOf(movie.getPremiereDate()));
            } else {
                stmt.setNull(10, java.sql.Types.DATE);
            }

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LocalDate getLocalDate(ResultSet rs, String column) throws SQLException {
        Date d = rs.getDate(column);
        return d != null ? d.toLocalDate() : null;
    }

    public void deleteById(int id) {

        String sql = "DELETE FROM film WHERE id_film = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Movie findById(int id) {

        String sql = """
        SELECT id_film, title, duration, genre, description,
               rating, base_price, image_url,
               run_from, run_to, premiere_date
        FROM film
        WHERE id_film = ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Movie(
                        rs.getInt("id_film"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("genre"),
                        rs.getString("description"),
                        rs.getBigDecimal("rating"),
                        rs.getBigDecimal("base_price"),
                        rs.getString("image_url"),
                        getLocalDate(rs, "run_from"),
                        getLocalDate(rs, "run_to"),
                        getLocalDate(rs, "premiere_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Caută filme după titlu (case-insensitive), folosind LIKE.
     * Exemplu: search="dune" -> titluri care conțin "dune".
     */
    public List<Movie> searchByTitle(String search) {
        List<Movie> movies = new ArrayList<>();

        String sql = """
        SELECT id_film, title, duration, genre, description,
               rating, base_price, image_url,
               run_from, run_to, premiere_date
        FROM film
        WHERE LOWER(title) LIKE ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + (search == null ? "" : search.toLowerCase()) + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie m = new Movie(
                            rs.getInt("id_film"),
                            rs.getString("title"),
                            rs.getInt("duration"),
                            rs.getString("genre"),
                            rs.getString("description"),
                            rs.getBigDecimal("rating"),
                            rs.getBigDecimal("base_price"),
                            rs.getString("image_url"),
                            getLocalDate(rs, "run_from"),
                            getLocalDate(rs, "run_to"),
                            getLocalDate(rs, "premiere_date")
                    );
                    movies.add(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT DISTINCT genre FROM film WHERE genre IS NOT NULL AND genre <> '' ORDER BY genre";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                genres.add(rs.getString("genre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }

    public List<Movie> findFiltered(String search, String genre) {
        List<Movie> movies = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT id_film, title, duration, genre, description,
               rating, base_price, image_url,
               run_from, run_to, premiere_date
        FROM film
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isBlank()) {
            sql.append(" AND LOWER(title) LIKE ?");
            params.add("%" + search.toLowerCase() + "%");
        }

        if (genre != null && !genre.isBlank()) {
            sql.append(" AND genre = ?");
            params.add(genre);
        }

        sql.append(" ORDER BY title");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie m = new Movie(
                            rs.getInt("id_film"),
                            rs.getString("title"),
                            rs.getInt("duration"),
                            rs.getString("genre"),
                            rs.getString("description"),
                            rs.getBigDecimal("rating"),
                            rs.getBigDecimal("base_price"),
                            rs.getString("image_url"),
                            getLocalDate(rs, "run_from"),
                            getLocalDate(rs, "run_to"),
                            getLocalDate(rs, "premiere_date")
                    );
                    movies.add(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }









}
