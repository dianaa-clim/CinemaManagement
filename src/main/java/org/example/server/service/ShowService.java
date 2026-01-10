package org.example.server.service;

import common.Show;
import org.example.server.dao.ShowDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    private final ShowDAO showDAO;

    public ShowService(ShowDAO showDAO) {
        this.showDAO = showDAO;
    }

    // ===================== FIND ALL =====================
    public List<Show> findAll() {
        return showDAO.findAll();
    }

    // ===================== FIND BY ID =====================
    public Show findById(int id) {
        return showDAO.findById(id);
    }

    // ===================== ADD =====================
    public void addShow(Show show) {
        showDAO.insert(show);
    }

    // ===================== DELETE =====================
    public void deleteShow(int id) {
        showDAO.deleteById(id);
    }
}
