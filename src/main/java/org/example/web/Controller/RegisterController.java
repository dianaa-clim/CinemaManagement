package org.example.web.Controller;

import org.example.web.store.UserStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class RegisterController {

    private final UserStore userStore;

    public RegisterController(UserStore userStore) {
        this.userStore = userStore;
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

        if (userStore.exists(username)) {
            model.addAttribute("error", "Username-ul există deja.");
            return "register";
        }

        userStore.create(username, password, Set.of(UserStore.Role.USER));

        return "redirect:/login?registered";
    }
}
