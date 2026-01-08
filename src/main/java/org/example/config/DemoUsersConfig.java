package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class DemoUsersConfig {

//    @Bean
//    InMemoryUserDetailsManager users() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("pass")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}

