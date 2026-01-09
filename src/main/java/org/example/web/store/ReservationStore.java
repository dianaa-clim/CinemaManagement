package org.example.web.store;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ReservationStore {

    public enum Status { CONFIRMED, CANCELED }

    public record Reservation(
            String id,
            int accountId,          // 🔴 NU username
            String showId,
            List<String> seats,
            Status status,
            LocalDateTime createdAt
    ) {}

    private final Map<String, Reservation> byId = new LinkedHashMap<>();

    /* ===================== CREATE ===================== */

    public Reservation create(int accountId, String showId, List<String> seats) {
        String id = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        Reservation r = new Reservation(
                id,
                accountId,
                showId,
                List.copyOf(seats),
                Status.CONFIRMED,
                LocalDateTime.now()
        );

        byId.put(id, r);
        return r;
    }

    /* ===================== READ ===================== */

    public List<Reservation> forAccount(int accountId) {
        return byId.values().stream()
                .filter(r -> r.accountId() == accountId)
                .sorted(Comparator.comparing(Reservation::createdAt).reversed())
                .toList();
    }

    public List<Reservation> activeForAccount(int accountId) {
        return forAccount(accountId).stream()
                .filter(r -> r.status() == Status.CONFIRMED)
                .toList();
    }

    public List<Reservation> canceledForAccount(int accountId) {
        return forAccount(accountId).stream()
                .filter(r -> r.status() == Status.CANCELED)
                .toList();
    }

    /* ===================== UPDATE ===================== */

    public boolean cancel(String id, int accountId) {
        Reservation r = byId.get(id);
        if (r == null) return false;
        if (r.accountId() != accountId) return false;
        if (r.status() == Status.CANCELED) return true;

        byId.put(id, new Reservation(
                r.id(),
                r.accountId(),
                r.showId(),
                r.seats(),
                Status.CANCELED,
                r.createdAt()
        ));

        return true;
    }

    /* ===================== STATS ===================== */

    public int countAll() {
        return byId.size();
    }
}
