package common;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Review implements Serializable {
    private int idReview;
    private int idClient;
    private int idFilm;
    private String textReview;
    private int rating;
    private LocalDateTime date;
    private String username;


    public Review() {}

    public Review(int idReview, int idClient, int idFilm, String textReview, int rating, LocalDateTime date) {
        this.idReview = idReview;
        this.idClient = idClient;
        this.idFilm = idFilm;
        this.textReview = textReview;
        this.rating = rating;
        this.date = date;
    }

    public int getIdReview() { return idReview; }
    public int getIdClient() { return idClient; }
    public int getIdFilm() { return idFilm; }
    public String getTextReview() { return textReview; }
    public int getRating() { return rating; }
    public LocalDateTime getDate() { return date; }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIdReview(int idReview) { this.idReview = idReview; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
    public void setIdFilm(int idFilm) { this.idFilm = idFilm; }
    public void setTextReview(String textReview) { this.textReview = textReview; }
    public void setRating(int rating) { this.rating = rating; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
