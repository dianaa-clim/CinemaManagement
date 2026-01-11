package org.example.config;

import org.example.server.dao.ClientDAO;
import org.example.server.dao.ReviewDAO;
import org.example.server.service.ClientService;
import org.example.server.service.ReviewService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
public class DaoServiceConfig {

    @Bean
    public ClientDAO clientDAO(Connection connection) {
        return new ClientDAO(connection);
    }

    @Bean
    public ReviewDAO reviewDAO(Connection connection) {
        return new ReviewDAO(connection);
    }

    @Bean
    public ClientService clientService(ClientDAO clientDAO) {
        return new ClientService(clientDAO);
    }

    @Bean
    public ReviewService reviewService(ReviewDAO reviewDAO) {
        return new ReviewService(reviewDAO);
    }
}

