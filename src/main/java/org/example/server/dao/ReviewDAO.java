package org.example.server.dao;

import common.Review;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private final Connection connection;

    public ReviewDAO(Connection connection) {
        this.connection = connection;
    }

//    public List<Review> findByFilm(int idFilm) {
//        List<Review> out = new ArrayList<>();
//        String sql = """
//            SELECT id_review, id_client, id_film, text_review, rating, date
//            FROM review
//            WHERE id_film = ?
//            ORDER BY date DESC
//        """;
//
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, idFilm);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) out.add(map(rs));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return out;
//    }

    public List<Review> findByFilm(int idFilm) {
        List<Review> out = new ArrayList<>();

        String sql = """
        SELECT r.id_review, r.id_client, r.id_film,
               r.text_review, r.rating, r.date,
               a.username
        FROM review r
        JOIN client c ON c.id_client = r.id_client
        JOIN account a ON a.id_account = c.id_account
        WHERE r.id_film = ?
        ORDER BY r.date DESC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idFilm);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = map(rs);
                    if (r != null) {
                        out.add(r);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;
    }


    public Review findByClientAndFilm(int idClient, int idFilm) {
        String sql = """
            SELECT id_review, id_client, id_film, text_review, rating, date
            FROM review
            WHERE id_client = ? AND id_film = ?
            LIMIT 1
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idClient);
            ps.setInt(2, idFilm);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveOrUpdate(int idClient, int idFilm, int rating, String textReview) {
        Review existing = findByClientAndFilm(idClient, idFilm);

        if (existing == null) {
            String ins = """
                INSERT INTO review (id_client, id_film, text_review, rating, date)
                VALUES (?, ?, ?, ?, NOW())
            """;
            try (PreparedStatement ps = connection.prepareStatement(ins)) {
                ps.setInt(1, idClient);
                ps.setInt(2, idFilm);
                ps.setString(3, textReview);
                ps.setInt(4, rating);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String upd = """
                UPDATE review
                SET text_review = ?, rating = ?, date = NOW()
                WHERE id_review = ?
            """;
            try (PreparedStatement ps = connection.prepareStatement(upd)) {
                ps.setString(1, textReview);
                ps.setInt(2, rating);
                ps.setInt(3, existing.getIdReview());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Double averageRating(int idFilm) {
        String sql = "SELECT AVG(rating) AS avg_rating FROM review WHERE id_film = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idFilm);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double v = rs.getDouble("avg_rating");
                    return rs.wasNull() ? null : v;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Review map(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("date");
        LocalDateTime dt = (ts == null) ? null : ts.toLocalDateTime();

        Review r = new Review(
                rs.getInt("id_review"),
                rs.getInt("id_client"),
                rs.getInt("id_film"),
                rs.getString("text_review"),
                rs.getInt("rating"),
                dt
        );

        // username există doar în query-ul cu JOIN
        try {
            r.setUsername(rs.getString("username"));
        } catch (SQLException ignored) {
            // nu avem coloana username în acest ResultSet (ex: findByClientAndFilm)
        }

        return r;
    }

}
