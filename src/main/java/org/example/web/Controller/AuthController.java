package org.example.web.Controller;

import common.Account;
import jakarta.servlet.http.HttpSession;
import org.example.server.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AuthController {

    private final AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request) {

        Account user = accountService.login(username, password);

        if (user == null) {
            return "redirect:/login?error";
        }

        // 🔥 invalidează sesiunea veche
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        // 🔥 creează sesiune NOUĂ
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("user", user);

        System.out.println("LOGIN USER = " + user.getUsername());
        System.out.println("LOGIN ROLE = " + user.getRole());

        if ("Admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/admin";
        }

        if ("Employee".equalsIgnoreCase(user.getRole())) {
            return "redirect:/staff";
        }

        return "redirect:/account";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 🔥 șterge complet sesiunea
        return "redirect:/login?logout";
    }


}
