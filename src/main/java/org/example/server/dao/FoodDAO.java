package org.example.server.dao;

import common.Food;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FoodDAO {

    private final Connection connection;

    public FoodDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Food> findAll() {
        List<Food> items = new ArrayList<>();

        String sql = "SELECT id_food, name, description, price FROM food";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                items.add(new Food(
                        rs.getInt("id_food"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public void insert(Food food) {
        String sql = "INSERT INTO food(name, description, price) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, food.getName());
            stmt.setString(2, food.getDescription());
            stmt.setBigDecimal(3, food.getPrice());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM food WHERE id_food = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
