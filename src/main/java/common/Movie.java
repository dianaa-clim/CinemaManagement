package common;

import java.io.Serializable;
import java.time.LocalDate;

public class Movie implements Serializable {
    private int idMovie;
    private String movieName;
    private String genre;
    private int duration;
    private String description;
    private LocalDate premiereDate;
    private float rating;
    private float price;

    public Movie(String movieName, String genre,
                 int duration, String description, LocalDate premiereDate, float rating, float price) {
        this.movieName = movieName;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
        this.premiereDate = premiereDate;
        this.rating = rating;
        this.price = price;
    }
    public Movie(int idMovie, String movieName, String genre,
                int duration, String description, LocalDate premiereDate, float rating, float price) {
        this.idMovie = idMovie;
        this.movieName = movieName;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
        this.premiereDate = premiereDate;
        this.rating = rating;
        this.price = price;
    }

    public int getIdMovie() {
        return idMovie;
    }
    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getPremiereDate() {
        return premiereDate;
    }
    public void setPremiereDate(LocalDate premiereDate) {
        this.premiereDate = premiereDate;
    }
    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}
