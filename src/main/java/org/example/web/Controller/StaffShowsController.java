package org.example.web.Controller;

import common.Show;
import org.example.server.service.MovieService;
import org.example.server.service.ShowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/shows")
public class StaffShowsController {

    private final ShowService showService;
    private final MovieService movieService;

    public StaffShowsController(ShowService showService,
                                MovieService movieService) {
        this.showService = showService;
        this.movieService = movieService;
    }

    // ===================== LIST SHOWS =====================
    @GetMapping
    public String shows(Model model) {
        model.addAttribute("shows", showService.findAll());
        return "staff/shows";
    }

    // ===================== ADD SHOW FORM =====================
    @GetMapping("/new")
    public String newShowForm(Model model) {
        model.addAttribute("show", new Show());
        model.addAttribute("movies", movieService.findAll());
        return "staff/show_new";
    }

    // ===================== SAVE SHOW =====================
    @PostMapping("/new")
    public String saveShow(@ModelAttribute Show show) {
        showService.addShow(show);
        return "redirect:/staff/shows";
    }

    // ===================== DELETE SHOW =====================
    @PostMapping("/{id}/delete")
    public String deleteShow(@PathVariable int id) {
        showService.deleteShow(id);
        return "redirect:/staff/shows";
    }
}
