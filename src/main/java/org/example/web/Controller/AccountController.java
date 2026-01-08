package org.example.web.Controller;

import org.example.web.store.ReservationStore;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    private final ReservationStore reservationStore;

    public AccountController(ReservationStore reservationStore) {
        this.reservationStore = reservationStore;
    }

    @GetMapping("/account")
    public String account(Authentication auth, Model model) {
        String username = auth.getName();

        model.addAttribute("username", username);
        model.addAttribute("activeReservations",
                reservationStore.activeForUser(username));
        model.addAttribute("canceledReservations",
                reservationStore.canceledForUser(username));

        return "account";
    }

    @PostMapping("/account/reservations/{id}/cancel")
    public String cancel(@PathVariable String id, Authentication auth) {
        reservationStore.cancel(id, auth.getName());
        return "redirect:/account";
    }
}

