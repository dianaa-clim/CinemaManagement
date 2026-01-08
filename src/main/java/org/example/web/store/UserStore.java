package org.example.web.store;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserStore {

    public enum Role { USER, EMPLOYEE, ADMIN }

    public record AppUser(String username, String passwordHash, Set<Role> roles) {}

    private final Map<String, AppUser> users = new LinkedHashMap<>();
    private final PasswordEncoder encoder;

    public UserStore(PasswordEncoder encoder) {
        this.encoder = encoder;

        // seed demo accounts
        users.put("user", new AppUser("user", encoder.encode("pass"), Set.of(Role.USER)));
        users.put("employee", new AppUser("employee", encoder.encode("pass"), Set.of(Role.EMPLOYEE)));
        users.put("admin", new AppUser("admin", encoder.encode("pass"), Set.of(Role.ADMIN)));
    }

    public Collection<AppUser> all() {
        return users.values();
    }

    public boolean exists(String username) {
        return users.containsKey(username);
    }

    public void create(String username, String rawPassword, Set<Role> roles) {
        users.put(username, new AppUser(username, encoder.encode(rawPassword), roles));
    }

    public void delete(String username) {
        users.remove(username);
    }

    public Optional<AppUser> find(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public void changePassword(String username, String rawPassword) {
        var u = users.get(username);
        if (u == null) return;
        users.put(username, new AppUser(username, encoder.encode(rawPassword), u.roles()));
    }
}
