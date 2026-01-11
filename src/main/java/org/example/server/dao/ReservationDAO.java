package org.example.server.dao;

import common.Reservation;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDAO {

    private final Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    /* ===================== CREATE ===================== */

    public void save(Reservation reservation) {

        String sql = """
            INSERT INTO reservation
            (id_account, id_film, id_cinema_room, id_seat,
             date_reservation, price_reservation,
             id_movie_schedule, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVE')
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getIdClient()); // aici trebuie să fie ACCOUNT ID (vezi fix la controller)
            stmt.setInt(2, reservation.getIdFilm());
            stmt.setInt(3, reservation.getIdCinemaRoom());
            stmt.setInt(4, reservation.getIdSeat());
            stmt.setDate(5, reservation.getDateReservation() != null
                    ? Date.valueOf(reservation.getDateReservation())
                    : null);
            stmt.setFloat(6, reservation.getPriceReservation());
            stmt.setInt(7, reservation.getIdMovieSchedule());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error in ReservationDAO.save()", e);
        }
    }

    /* ===================== READ ===================== */

    public List<Reservation> findActiveByClient(int clientId) {

        List<Reservation> list = new ArrayList<>();

        String sql = """
        SELECT *
        FROM reservation
        WHERE id_account = ? AND status = 'ACTIVE'
        ORDER BY date_reservation DESC
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapReservation(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in findActiveByClient(clientId=" + clientId + ")", e);
        }

        return list;
    }


    public List<Reservation> findCanceledByClient(int clientId) {

        List<Reservation> list = new ArrayList<>();

        String sql = """
        SELECT *
        FROM reservation
        WHERE id_account = ? AND status = 'CANCELED'
        ORDER BY date_reservation DESC
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapReservation(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in findCanceledByClient(clientId=" + clientId + ")", e);
        }

        return list;
    }

    public int countAll() {

        String sql = "SELECT COUNT(*) FROM reservation";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("DB error in countAll()", e);
        }
    }

    /* ===================== UPDATE ===================== */

    public void cancel(int reservationId, int accountId) {

        String sql = """
            UPDATE reservation
            SET status = 'CANCELED'
            WHERE id_reservation = ? AND id_account = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "DB error in cancel(reservationId=" + reservationId + ", accountId=" + accountId + ")", e
            );
        }
    }

    /* ===================== MAPPING ===================== */

    private Reservation mapReservation(ResultSet rs) throws SQLException {

        Date sqlDate = rs.getDate("date_reservation");

        return new Reservation(
                rs.getInt("id_reservation"),
                rs.getInt("id_account"),
                rs.getInt("id_film"),
                rs.getInt("id_cinema_room"),
                rs.getInt("id_seat"),
                sqlDate != null ? sqlDate.toLocalDate() : null,
                rs.getFloat("price_reservation"),
                rs.getInt("id_movie_schedule")
        );
    }

    /* ===================== EXTRA ===================== */

    public List<Integer> findOccupiedSeatIdsByMovieSchedule(int movieScheduleId) {

        List<Integer> seats = new ArrayList<>();

        String sql = """
            SELECT id_seat
            FROM reservation
            WHERE id_movie_schedule = ?
              AND status = 'ACTIVE'
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movieScheduleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    seats.add(rs.getInt("id_seat"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "DB error in findOccupiedSeatIdsByMovieSchedule(movieScheduleId=" + movieScheduleId + ")", e
            );
        }

        return seats;
    }

    public Integer findClientIdByAccountId(int accountId) {

        String sql = "SELECT id_client FROM client WHERE id_account = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("id_client");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error in findClientIdByAccountId(accountId=" + accountId + ")", e);
        }
    }

    public Integer findMovieScheduleIdForShow(int filmId, java.time.LocalDate date, java.time.LocalTime time) {

        String sql = """
        SELECT id_movie_schedule
        FROM movie_schedule
        WHERE id_film = ?
          AND DATE(start_time) = ?
          AND TIME(start_time) = ?
        LIMIT 1
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setTime(3, java.sql.Time.valueOf(time));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("id_movie_schedule");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error in findMovieScheduleIdForShow()", e);
        }
    }




}
