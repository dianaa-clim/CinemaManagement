package org.example.web.Controller;

import org.example.web.store.CinemaStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffController {

    @GetMapping("/staff")
    public String staffHome(Model model) {
        // dashboard simplu
        model.addAttribute("moviesCount", CinemaStore.MOVIES.size());
        model.addAttribute("showsCount", CinemaStore.SHOWS.size());
        model.addAttribute("roomsCount", 5); // dummy
        return "staff/index";
    }
}

