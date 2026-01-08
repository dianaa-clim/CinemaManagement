package org.example.web.Controller;

import org.example.web.store.CinemaStore;
import org.example.web.store.ReservationStore;
import org.example.web.store.UserStore;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserStore userStore;
    private final ReservationStore reservationStore;

    public AdminController(UserStore userStore, ReservationStore reservationStore) {
        this.userStore = userStore;
        this.reservationStore = reservationStore;
    }

    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("users", userStore.all());
        model.addAttribute("moviesCount", CinemaStore.MOVIES.size());
        model.addAttribute("showsCount", CinemaStore.SHOWS.size());

        // statistici simple (dacă ai metoda all în ReservationStore; dacă nu, vezi nota mai jos)
        model.addAttribute("reservationsCount", reservationStore.countAll());

        return "admin/index";
    }

    // Angajează (creează EMPLOYEE)
    @PostMapping("/employees/create")
    public String createEmployee(@RequestParam String username,
                                 @RequestParam String password,
                                 Model model) {

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return "redirect:/admin?err";
        }

        if (userStore.exists(username)) {
            return "redirect:/admin?exists";
        }

        userStore.create(username, password, Set.of(UserStore.Role.EMPLOYEE));
        return "redirect:/admin?created";
    }

    // Concediază / demisie (șterge cont)
    @PostMapping("/users/{username}/delete")
    public String deleteUser(@PathVariable String username, Authentication auth) {

        // nu lăsăm admin să se șteargă singur (safe)
        if (auth != null && auth.getName().equals(username)) {
            return "redirect:/admin?selfdelete";
        }

        userStore.delete(username);
        return "redirect:/admin?deleted";
    }
}

