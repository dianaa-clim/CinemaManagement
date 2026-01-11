package org.example.server.service;

import common.Review;
import org.example.server.dao.ReviewDAO;

import java.util.List;

public class ReviewService {
    private final ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public List<Review> getReviewsForFilm(int idFilm) {
        return reviewDAO.findByFilm(idFilm);
    }

    public Double getAverageRating(int idFilm) {
        return reviewDAO.averageRating(idFilm);
    }

    public void addOrUpdateReview(int idClient, int idFilm, int rating, String textReview) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating invalid");
        if (textReview == null || textReview.isBlank()) throw new IllegalArgumentException("Review gol");
        if (textReview.length() > 1000) throw new IllegalArgumentException("Max 1000 caractere");

        reviewDAO.saveOrUpdate(idClient, idFilm, rating, textReview.trim());
    }
}
