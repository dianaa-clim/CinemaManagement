package org.example.web.store;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ReservationStore {

    public enum Status { CONFIRMED, CANCELED }

    public record Reservation(
            String id,
            String username,
            String showId,
            List<String> seats,
            Status status,
            LocalDateTime createdAt
    ) {}

    private final Map<String, Reservation> byId = new LinkedHashMap<>();

    public Reservation create(String username, String showId, List<String> seats) {
        String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Reservation r = new Reservation(
                id, username, showId,
                List.copyOf(seats),
                Status.CONFIRMED,
                LocalDateTime.now()
        );
        byId.put(id, r);
        return r;
    }

    public List<Reservation> forUser(String username) {
        return byId.values().stream()
                .filter(r -> r.username().equals(username))
                .sorted(Comparator.comparing(Reservation::createdAt).reversed())
                .toList();
    }

    public boolean cancel(String id, String username) {
        Reservation r = byId.get(id);
        if (r == null) return false;
        if (!r.username().equals(username)) return false;
        if (r.status() == Status.CANCELED) return true;

        byId.put(id, new Reservation(
                r.id(), r.username(), r.showId(), r.seats(),
                Status.CANCELED, r.createdAt()
        ));
        return true;
    }

    public List<Reservation> activeForUser(String username) {
        return forUser(username).stream()
                .filter(r -> r.status() == Status.CONFIRMED)
                .toList();
    }

    public List<Reservation> canceledForUser(String username) {
        return forUser(username).stream()
                .filter(r -> r.status() == Status.CANCELED)
                .toList();
    }

    public int countAll() {
        return byId.size(); // sau colecția ta internă
    }


}

