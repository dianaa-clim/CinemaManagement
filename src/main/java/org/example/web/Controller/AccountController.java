package org.example.web.Controller;

import common.Account;
import jakarta.servlet.http.HttpSession;
import org.example.server.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    private final ReservationService reservationService;

    public AccountController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/account")
    public String account(HttpSession session, Model model) {

        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute(
                "activeReservations",
                reservationService.getActiveReservationsForUser(user.getIdAccount())
        );
        model.addAttribute(
                "canceledReservations",
                reservationService.getCanceledReservationsForUser(user.getIdAccount())
        );

        return "account";
    }
}
