package common;

import java.io.Serializable;

public class CinemaRoom implements Serializable {
    private int idCinemaRoom;
    private String cinemaRoomName;
    private int capacitate;
    private String roomType;

    public CinemaRoom(String cinemaRoomName, int capacitate, String roomType) {
        this.cinemaRoomName = cinemaRoomName;
        this.capacitate = capacitate;
        this.roomType = roomType;
    }
    public CinemaRoom(int idCinemaRoom, String cinemaRoomName, int capacitate, String roomType) {
        this.idCinemaRoom = idCinemaRoom;
        this.cinemaRoomName = cinemaRoomName;
        this.capacitate = capacitate;
        this.roomType = roomType;
    }

    public int getIdCinemaRoom() {
        return idCinemaRoom;
    }
    public void setIdCinemaRoom(int idCinemaRoom) {
        this.idCinemaRoom = idCinemaRoom;
    }
    public String getCinemaRoomName() {
        return cinemaRoomName;
    }
    public void setCinemaRoomName(String cinemaRoomName) {
        this.cinemaRoomName = cinemaRoomName;
    }
    public int getCapacitate() {
        return capacitate;
    }
    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }
    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
