package org.example.server.service;

import common.Room;
import org.example.server.dao.RoomDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomDAO roomDAO;

    public RoomService(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public List<Room> findAll() {
        return roomDAO.findAll();
    }

    public void addRoom(Room room) {
        roomDAO.insert(room);
    }

    public boolean canDelete(int roomId) {
        return !roomDAO.hasShows(roomId);
    }

    public void deleteRoom(int roomId) {
        roomDAO.delete(roomId);
    }
}
