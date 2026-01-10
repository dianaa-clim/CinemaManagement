package org.example.web.controller;

import common.Food;
import org.example.server.service.FoodService;   // ✅ IMPORTUL LIPSĂ
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // ================= CLIENT FOOD PAGE =================
    @GetMapping("/food")
    public String foodPage(Model model) {
        model.addAttribute("items", foodService.getAllFood());
        return "food";
    }
}
