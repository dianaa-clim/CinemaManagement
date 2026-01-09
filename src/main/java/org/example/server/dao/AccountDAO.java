package org.example.server.dao;

import common.Account;
import common.Employee;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDAO {

    private final Connection connection;

    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    /* ================= LOGIN ================= */

    public Account findByUsernameAndPassword(String username, String password) {

        String sql = """
            SELECT id_account, role, name, username, phone_number, password
            FROM account
            WHERE username = ? AND password = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /* ================= REGISTER ================= */

    public void insertClient(String username, String password) {

        String sql = """
            INSERT INTO account (role, name, username, phone_number, password)
            VALUES ('Client', ?, ?, NULL, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ================= ADMIN ================= */

    public List<Account> findAll() {

        List<Account> accounts = new ArrayList<>();

        String sql = """
            SELECT id_account, role, name, username, phone_number, password
            FROM account
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapAccount(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public void insertEmployee(String username, String password) {

        String sql = """
            INSERT INTO account (role, name, username, phone_number, password)
            VALUES ('Employee', ?, ?, NULL, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByUsername(String username) {

        String sql = "DELETE FROM account WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsByUsername(String username) {

        String sql = "SELECT 1 FROM account WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /* ================= MAP ================= */

    private Account mapAccount(ResultSet rs) throws SQLException {

        int id = rs.getInt("id_account");
        String role = rs.getString("role");
        String name = rs.getString("name");
        String username = rs.getString("username");
        String phone = rs.getString("phone_number");
        String password = rs.getString("password");

        if ("Employee".equalsIgnoreCase(role)) {
            return new Employee(id, role, name, username, phone, password);
        }

        return new Account(id, role, name, username, phone, password);
    }
    public Account findByUsername(String username) {

        String sql = """
        SELECT id_account, role, name, username, phone_number, password
        FROM account
        WHERE username = ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertAdmin(String username, String password) {

        String sql = """
        INSERT INTO account (role, name, username, phone_number, password)
        VALUES ('Admin', ?, ?, NULL, ?)
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "Administrator");
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
