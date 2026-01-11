package org.example.web.Controller;

import common.Account;
import jakarta.servlet.http.HttpSession;
import org.example.server.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    private final ReservationService reservationService;

    public AccountController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/account")
    public String account(HttpSession session, Model model) {

        Account user = (Account) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);

        model.addAttribute("activeReservations",
                reservationService.getActiveReservationsForUser(user.getIdAccount()));

        model.addAttribute("canceledReservations",
                reservationService.getCanceledReservationsForUser(user.getIdAccount()));

        return "account";
    }

    @PostMapping("/account/reservations/{id}/cancel")
    public String cancelReservation(@PathVariable("id") int reservationId,
                                    HttpSession session) {

        Account user = (Account) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Integer clientId = reservationService.getClientIdByAccountId(user.getIdAccount());
        if (clientId == null) return "redirect:/account";

        reservationService.cancelReservation(reservationId, clientId);
        return "redirect:/account";
    }
}

