package org.example.web.Controller;

import common.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.server.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

        // 🔥 invalidate old session
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        // 🔥 create new session
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);

        System.out.println("LOGIN USER = " + user.getUsername());
        System.out.println("LOGIN ROLE = " + user.getRole());

        String role = user.getRole();

        if ("ADMIN".equalsIgnoreCase(role)) {
            return "redirect:/admin";
        }

        if ("EMPLOYEE".equalsIgnoreCase(role)) {
            return "redirect:/staff";
        }

        return "redirect:/account";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
