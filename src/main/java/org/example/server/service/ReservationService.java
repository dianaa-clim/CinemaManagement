package org.example.server.service;

import common.Reservation;
import org.example.server.dao.ReservationDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> getActiveReservationsForUser(int accountId) {
        return reservationDAO.findActiveByAccount(accountId);
    }

    public List<Reservation> getCanceledReservationsForUser(int accountId) {
        return reservationDAO.findCanceledByAccount(accountId);
    }

    public int countAll() {
        return reservationDAO.countAll();
    }

    public void createReservation(Reservation reservation) {
        reservationDAO.save(reservation);
    }

    public void cancelReservation(int reservationId, int accountId) {
        reservationDAO.cancel(reservationId, accountId);
    }
}
