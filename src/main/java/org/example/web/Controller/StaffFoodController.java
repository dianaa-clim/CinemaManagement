package org.example.web.controller;

import common.Food;
import org.example.server.service.FoodService;   // ✅ IMPORTUL LIPSĂ
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/staff/food")
public class StaffFoodController {

    private final FoodService foodService;

    public StaffFoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // ================= LIST =================
    @GetMapping
    public String manageFood(Model model) {
        model.addAttribute("items", foodService.getAllFood());
        return "staff/food";
    }

    // ================= ADD FORM =================
    @GetMapping("/new")
    public String newFoodForm(Model model) {
        model.addAttribute("food", new Food());
        return "staff/food_new";
    }

    // ================= ADD SAVE =================
    @PostMapping("/new")
    public String addFood(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price
    ) {
        Food food = new Food();
        food.setName(name);
        food.setDescription(description);
        food.setPrice(price);

        foodService.addFood(food);

        return "redirect:/staff/food";
    }

    // ================= DELETE =================
    @PostMapping("/{id}/delete")
    public String deleteFood(@PathVariable int id) {
        foodService.deleteFood(id);
        return "redirect:/staff/food";
    }
}
