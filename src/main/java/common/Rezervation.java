package common;

import java.io.Serializable;
import java.time.LocalDate;

public class Rezervation implements Serializable {
    private int idRezervation;
    private int idClient;
    private int idFilm;
    private int idCinemaRoom;
    private int idSeat;
    private LocalDate dateRezervation;
    private float priceRezervation;
    private int idMovieSchedule;

    public Rezervation(int idClient, int idFilm, int idCinemaRoom,
                       int idSeat, LocalDate dateRezervation, float priceRezervation, int idMovieSchedule) {
        this.idClient = idClient;
        this.idFilm = idFilm;
        this.idCinemaRoom = idCinemaRoom;
        this.idSeat = idSeat;
        this.dateRezervation = dateRezervation;
        this.priceRezervation = priceRezervation;
        this.idMovieSchedule = idMovieSchedule;
    }
    public Rezervation(int idRezervation, int idClient, int idFilm, int idCinemaRoom,
                       int idSeat, LocalDate dateRezervation, float priceRezervation, int idMovieSchedule) {
        this.idRezervation = idRezervation;
        this.idClient = idClient;
        this.idFilm = idFilm;
        this.idCinemaRoom = idCinemaRoom;
        this.idSeat = idSeat;
        this.dateRezervation = dateRezervation;
        this.priceRezervation = priceRezervation;
        this.idMovieSchedule = idMovieSchedule;
    }

    public int getIdRezervation() {
        return idRezervation;
    }
    public void setIdRezervation(int idRezervation) {
        this.idRezervation = idRezervation;
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
    public LocalDate getDateRezervation() {
        return dateRezervation;
    }
    public void setDateRezervation(LocalDate dateRezervation) {
        this.dateRezervation = dateRezervation;
    }
    public float getPriceRezervation() {
        return priceRezervation;
    }
    public void setPriceRezervation(float priceRezervation) {
        this.priceRezervation = priceRezervation;
    }
    public int getIdMovieSchedule() {
        return idMovieSchedule;
    }
    public void setIdMovieSchedule(int idMovieSchedule) {
        this.idMovieSchedule = idMovieSchedule;
    }
}
