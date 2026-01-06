package common;

import java.io.Serializable;
import java.time.LocalDate;

public class MovieSchedule implements Serializable {
    private int idMovieSchedule;
    private int idMovie;
    private int idCinemaRoom;
    private LocalDate dateTime;
    private float price;

    public MovieSchedule(int idMovie, int idCinemaRoom, LocalDate dateTime, float price) {
        this.idMovie = idMovie;
        this.idCinemaRoom = idCinemaRoom;
        this.dateTime = dateTime;
        this.price = price;
    }
    public MovieSchedule(int idMovieSchedule, int idMovie, int idCinemaRoom, LocalDate dateTime, float price) {
        this.idMovieSchedule = idMovieSchedule;
        this.idMovie = idMovie;
        this.idCinemaRoom = idCinemaRoom;
        this.dateTime = dateTime;
        this.price = price;
    }
    public int getIdMovieSchedule() {
        return idMovieSchedule;
    }
    public void setIdMovieSchedule(int idMovieSchedule) {
        this.idMovieSchedule = idMovieSchedule;
    }
    public int getIdMovie() {
        return idMovie;
    }
    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }
    public int getIdCinemaRoom() {
        return idCinemaRoom;
    }
    public void setIdCinemaRoom(int idCinemaRoom) {
        this.idCinemaRoom = idCinemaRoom;
    }
    public LocalDate getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }
    public float getPrice() {
        return price;
    }
}
