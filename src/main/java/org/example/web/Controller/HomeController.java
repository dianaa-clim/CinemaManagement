package org.example.web.Controller;

import org.example.web.store.CinemaStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        model.addAttribute("nowPlaying",
                CinemaStore.getMoviesPlayingOn(LocalDate.now())
        );
        return "index";
    }
}
