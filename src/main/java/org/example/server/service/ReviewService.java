package org.example.server.service;

import common.Review;
import org.springframework.stereotype.Service;
import org.example.server.dao.ReviewDAO;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    /**
     * Adaugă un review (rating 1–5)
     */
    public boolean addReview(Review review) {

        if (review.getRating() < 1 || review.getRating() > 5) {
            return false;
        }

        reviewDAO.save(review);
        return true;
    }

    public List<Review> getReviewsForMovie(int idFilm) {
        return reviewDAO.findByFilm(idFilm);
    }
}
