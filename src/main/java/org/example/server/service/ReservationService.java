package org.example.server.service;

import common.Reservation;
import org.example.server.dao.ReservationDAO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> getActiveReservationsForUser(int accountId) {
        Integer clientId = reservationDAO.findClientIdByAccountId(accountId);
        if (clientId == null) return Collections.emptyList();
        return reservationDAO.findActiveByClient(clientId);
    }

    public List<Reservation> getCanceledReservationsForUser(int accountId) {
        Integer clientId = reservationDAO.findClientIdByAccountId(accountId);
        if (clientId == null) return Collections.emptyList();
        return reservationDAO.findCanceledByClient(clientId);
    }

    public int countAll() {
        return reservationDAO.countAll();
    }

    public void createReservation(Reservation reservation) {
        reservationDAO.save(reservation);
    }
    public Integer getClientIdByAccountId(int accountId) {
        return reservationDAO.findClientIdByAccountId(accountId);
    }

    public void cancelReservation(int reservationId, int accountId) {
        reservationDAO.cancel(reservationId, accountId);
    }
}
