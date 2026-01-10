package org.example.server.dao;

import common.Show;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShowDAO {

    private final Connection connection;

    public ShowDAO(Connection connection) {
        this.connection = connection;
    }

    // ===================== FIND ALL =====================
    public List<Show> findAll() {

        List<Show> shows = new ArrayList<>();

        String sql = """
            SELECT id_show, id_film, show_date, show_time
            FROM showtime
            ORDER BY show_date, show_time
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Show show = new Show(
                        rs.getInt("id_show"),
                        rs.getInt("id_film"),
                        rs.getDate("show_date").toLocalDate(),
                        rs.getTime("show_time").toLocalTime()
                );
                shows.add(show);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return shows;
    }

    // ===================== FIND BY ID =====================
    public Show findById(int id) {

        String sql = """
            SELECT id_show, id_film, show_date, show_time
            FROM showtime
            WHERE id_show = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Show(
                        rs.getInt("id_show"),
                        rs.getInt("id_film"),
                        rs.getDate("show_date").toLocalDate(),
                        rs.getTime("show_time").toLocalTime()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===================== INSERT =====================
    public void insert(Show show) {

        String sql = """
            INSERT INTO showtime (id_film, show_date, show_time)
            VALUES (?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, show.getMovieId());
            stmt.setDate(2, Date.valueOf(show.getDate()));
            stmt.setTime(3, Time.valueOf(show.getTime()));

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===================== DELETE =====================
    public void deleteById(int id) {

        String sql = "DELETE FROM showtime WHERE id_show = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
