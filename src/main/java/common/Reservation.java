package common;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {

    private int idReservation;
    private int idClient;
    private int idFilm;
    private int idCinemaRoom;
    private int idSeat;
    private LocalDate dateReservation;
    private float priceReservation;
    private int idMovieSchedule;

    public Reservation(int idClient, int idFilm, int idCinemaRoom,
                       int idSeat, LocalDate dateReservation,
                       float priceReservation, int idMovieSchedule) {

        this.idClient = idClient;
        this.idFilm = idFilm;
        this.idCinemaRoom = idCinemaRoom;
        this.idSeat = idSeat;
        this.dateReservation = dateReservation;
        this.priceReservation = priceReservation;
        this.idMovieSchedule = idMovieSchedule;
    }

    public Reservation(int idReservation, int idClient, int idFilm, int idCinemaRoom,
                       int idSeat, LocalDate dateReservation,
                       float priceReservation, int idMovieSchedule) {

        this.idReservation = idReservation;
        this.idClient = idClient;
        this.idFilm = idFilm;
        this.idCinemaRoom = idCinemaRoom;
        this.idSeat = idSeat;
        this.dateReservation = dateReservation;
        this.priceReservation = priceReservation;
        this.idMovieSchedule = idMovieSchedule;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
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

    public int getIdCinemaRoom() {
        return idCinemaRoom;
    }

    public void setIdCinemaRoom(int idCinemaRoom) {
        this.idCinemaRoom = idCinemaRoom;
    }

    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public float getPriceReservation() {
        return priceReservation;
    }

    public void setPriceReservation(float priceReservation) {
        this.priceReservation = priceReservation;
    }

    public int getIdMovieSchedule() {
        return idMovieSchedule;
    }

    public void setIdMovieSchedule(int idMovieSchedule) {
        this.idMovieSchedule = idMovieSchedule;
    }
}
