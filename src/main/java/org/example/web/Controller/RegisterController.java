package org.example.web.Controller;

import org.example.server.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final AccountService accountService;

    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {

        if (username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Toate câmpurile sunt obligatorii.");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Parolele nu coincid.");
            return "register";
        }

        accountService.register(username, password);
        return "redirect:/login";
    }
}
