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

            stmt.setInt(1, reservation.getIdClient());
            stmt.setInt(2, reservation.getIdFilm());
            stmt.setInt(3, reservation.getIdCinemaRoom());
            stmt.setInt(4, reservation.getIdSeat());
            stmt.setDate(5, Date.valueOf(reservation.getDateReservation()));
            stmt.setFloat(6, reservation.getPriceReservation());
            stmt.setInt(7, reservation.getIdMovieSchedule());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ===================== READ ===================== */

    public List<Reservation> findActiveByAccount(int accountId) {

        List<Reservation> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM reservation
            WHERE id_account = ? AND status = 'ACTIVE'
            ORDER BY date_reservation DESC
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapReservation(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Reservation> findCanceledByAccount(int accountId) {

        List<Reservation> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM reservation
            WHERE id_account = ? AND status = 'CANCELED'
            ORDER BY date_reservation DESC
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapReservation(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return 0;
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
            e.printStackTrace();
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
}
