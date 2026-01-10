package common;

import java.time.LocalDate;
import java.time.LocalTime;

public class Show {

    private int idShow;
    private int movieId;
    private LocalDate date;
    private LocalTime time;

    public Show() {
    }

    public Show(int idShow, int movieId, LocalDate date, LocalTime time) {
        this.idShow = idShow;
        this.movieId = movieId;
        this.date = date;
        this.time = time;
    }

    // ===== GETTERS & SETTERS =====

    public int getIdShow() {
        return idShow;
    }

    public void setIdShow(int idShow) {
        this.idShow = idShow;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
