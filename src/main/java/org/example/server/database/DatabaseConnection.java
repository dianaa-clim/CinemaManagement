package org.example.server.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConnection {

    @Bean
    public Connection connection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cinema?useSSL=false&serverTimezone=UTC",
                "root",
                "Diana*1234"
        );
    }
}
