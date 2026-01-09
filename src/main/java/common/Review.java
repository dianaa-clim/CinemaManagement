package common;

import java.io.Serializable;
import java.time.LocalDate;

public class Review implements Serializable {
    private int idReview;
    private int idClient;
    private int idFilm;
    private String textReview;
    private int rating;
    private LocalDate date;

    public Review(int idFilm, String textReview, int rating, LocalDate date) {
        this.idFilm = idFilm;
        this.textReview = textReview;
        this.rating = rating;
        this.date = date;
    }
    public Review(int idClient, int idFilm, String textReview, int rating, LocalDate date) {
        this.idClient = idClient;
        this.idFilm = idFilm;
        this.textReview = textReview;
        this.rating = rating;
        this.date = date;
    }
    public int getIdReview() {
        return idReview;
    }
    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public int getIdFilm() {
        return idFilm;
    }
    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }
    public String getTextReview() {
        return textReview;
    }
    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
