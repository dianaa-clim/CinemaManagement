package org.example.config;

import org.example.server.dao.AccountDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(AccountDAO accountDAO) {
        return args -> {

            String adminUsername = "admin";
            String adminPassword = "admin123";

            if (!accountDAO.existsByUsername(adminUsername)) {
                accountDAO.insertAdmin(adminUsername, adminPassword);
                System.out.println("✔ Admin creat automat (admin / admin123)");
            }
        };
    }
}
