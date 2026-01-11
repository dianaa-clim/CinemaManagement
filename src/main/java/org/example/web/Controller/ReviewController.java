package org.example.web.Controller;

import common.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.server.service.ClientService;
import org.example.server.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final ClientService clientService;

    public ReviewController(ReviewService reviewService, ClientService clientService) {
        this.reviewService = reviewService;
        this.clientService = clientService;
    }

    @PostMapping("/reviews")
    public String postReview(
            @RequestParam("idFilm") int idFilm,
            @RequestParam("rating") int rating,
            @RequestParam("textReview") String textReview,
            HttpSession session,
            HttpServletRequest request
    ) {
        Account user = (Account) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        int idClient = clientService.getClientIdByAccountId(user.getIdAccount());
        reviewService.addOrUpdateReview(idClient, idFilm, rating, textReview);

        String ref = request.getHeader("Referer");
        return (ref != null) ? "redirect:" + ref : "redirect:/movies";
    }
}

