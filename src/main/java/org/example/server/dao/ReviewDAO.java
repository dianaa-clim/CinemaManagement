package org.example.server.dao;

import common.Review;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDAO {

    private final Connection connection;

    public ReviewDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adaugă un review nou
     */
    public void save(Review review) {

        String sql = """
            INSERT INTO reviews (id_client, id_film, text_review, rating, review_date)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, review.getIdClient());
            stmt.setInt(2, review.getIdFilm());
            stmt.setString(3, review.getTextReview());
            stmt.setInt(4, review.getRating());
            stmt.setDate(5, Date.valueOf(review.getDate()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returnează toate review-urile unui film
     */
    public List<Review> findByFilm(int idFilm) {

        List<Review> reviews = new ArrayList<>();

        String sql = """
            SELECT id_review, id_client, id_film, text_review, rating, review_date
            FROM reviews
            WHERE id_film = ?
            ORDER BY review_date DESC
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idFilm);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(mapReview(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    /**
     * Returnează review-urile unui client
     */
    public List<Review> findByClient(int idClient) {

        List<Review> reviews = new ArrayList<>();

        String sql = """
            SELECT id_review, id_client, id_film, text_review, rating, review_date
            FROM reviews
            WHERE id_client = ?
            ORDER BY review_date DESC
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(mapReview(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    /**
     * Mapare ResultSet → Review
     */
    private Review mapReview(ResultSet rs) throws SQLException {

        Date sqlDate = rs.getDate("review_date");

        Review review = new Review(
                rs.getInt("id_client"),
                rs.getInt("id_film"),
                rs.getString("text_review"),
                rs.getInt("rating"),
                sqlDate != null ? sqlDate.toLocalDate() : null
        );

        // dacă vrei să păstrezi și idReview
        review.setIdClient(rs.getInt("id_client"));
        review.setIdFilm(rs.getInt("id_film"));

        return review;
    }
}
