package org.example.web.Controller;

import common.Account;
import jakarta.servlet.http.HttpSession;
import org.example.server.service.AccountService;
import org.example.server.service.ReservationService;
import org.example.web.store.CinemaStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;
    private final ReservationService reservationService;

    public AdminController(AccountService accountService,
                           ReservationService reservationService) {
        this.accountService = accountService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String adminHome(HttpSession session, Model model) {

        Account admin = (Account) session.getAttribute("user");

        System.out.println("ADMIN ROLE = " + admin.getRole());

        if (admin == null || !"Admin".equalsIgnoreCase(admin.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("users", accountService.findAll());
        model.addAttribute("moviesCount", CinemaStore.MOVIES.size());
        model.addAttribute("showsCount", CinemaStore.SHOWS.size());
        model.addAttribute("reservationsCount", reservationService.countAll());

        return "admin/index";
    }

    // Creează EMPLOYEE
    @PostMapping("/employees/create")
    public String createEmployee(@RequestParam String username,
                                 @RequestParam String password,
                                 HttpSession session) {

        Account admin = (Account) session.getAttribute("user");

        if (admin == null || !"Admin".equalsIgnoreCase(admin.getRole())) {
            return "redirect:/login";
        }

        accountService.createEmployee(username, password);
        return "redirect:/admin?created";
    }

    // Șterge utilizator
    @PostMapping("/users/{username}/delete")
    public String deleteUser(@PathVariable String username,
                             HttpSession session) {

        Account admin = (Account) session.getAttribute("user");

        if (admin == null || !"Admin".equalsIgnoreCase(admin.getRole())) {
            return "redirect:/login";
        }

        if (admin.getUsername().equals(username)) {
            return "redirect:/admin?selfdelete";
        }

        accountService.deleteByUsername(username);
        return "redirect:/admin?deleted";
    }
}
