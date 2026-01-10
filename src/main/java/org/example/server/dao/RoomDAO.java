package org.example.server.dao;

import common.Room;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomDAO {

    private final Connection connection;

    public RoomDAO(Connection connection) {
        this.connection = connection;
    }

    // ================= FIND ALL =================
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT id_cinema_room, name, seats FROM cinema_room ORDER BY name";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id_cinema_room"),
                        rs.getString("name"),
                        rs.getInt("seats")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rooms;
    }

    // ================= INSERT =================
    public void insert(Room room) {
        String sql = "INSERT INTO cinema_room (name, seats) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, room.getName());
            stmt.setInt(2, room.getSeats());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE =================
    public void delete(int id) {
        String sql = "DELETE FROM cinema_room WHERE id_cinema_room = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= CHECK SHOWS =================
    public boolean hasShows(int roomId) {
        String sql = "SELECT COUNT(*) FROM showtime WHERE id_cinema_room = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
