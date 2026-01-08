package org.example.config;

import org.example.web.store.UserStore;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserStore userStore;

    public UserDetailsServiceImpl(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userStore.find(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        var authorities = user.roles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.username(),
                user.passwordHash(),
                authorities
        );
    }
}

