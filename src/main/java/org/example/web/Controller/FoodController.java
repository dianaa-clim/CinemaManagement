package org.example.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodController {

    @GetMapping("/food")
    public String food() {
        return "food";
    }

}

