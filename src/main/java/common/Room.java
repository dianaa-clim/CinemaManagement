package common;

public class Room {

    private int idCinemaRoom;
    private String name;
    private int seats;

    public Room() {}

    public Room(int idCinemaRoom, String name, int seats) {
        this.idCinemaRoom = idCinemaRoom;
        this.name = name;
        this.seats = seats;
    }

    public int getIdCinemaRoom() {
        return idCinemaRoom;
    }

    public void setIdCinemaRoom(int idCinemaRoom) {
        this.idCinemaRoom = idCinemaRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
