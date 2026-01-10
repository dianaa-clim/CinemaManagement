package org.example.server.service;

import common.Food;
import org.example.server.dao.FoodDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodDAO foodDAO;

    public FoodService(FoodDAO foodDAO) {
        this.foodDAO = foodDAO;
    }

    public List<Food> getAllFood() {
        return foodDAO.findAll();
    }

    public void addFood(Food food) {
        foodDAO.insert(food);
    }

    public void deleteFood(int id) {
        foodDAO.deleteById(id);
    }
}
