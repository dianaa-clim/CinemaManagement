package org.example.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDAO {
    private final Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    public Integer findClientIdByAccountId(int idAccount) {
        String sql = "SELECT id_client FROM client WHERE id_account = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAccount);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_client");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
