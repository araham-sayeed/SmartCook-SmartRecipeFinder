package com.example.smartcook.controller;

import com.example.smartcook.service.RGService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rg")
@CrossOrigin(origins = "http://localhost:3000") // local host of react
public class RGController {

    private final RGService rgService;

    public RGController(RGService rgService) {
        this.rgService = rgService;
    }

    // Get all meal types like "Breakfast", "Lunch"
    @GetMapping("/meals")
    public List<String> getMealTypes() {
        return rgService.getAllMealTypes().keySet().stream().collect(Collectors.toList());
    }

    // Get dishes under a meal type like "Omelette", "Pancakes"
    @GetMapping("/meals/{mealType}/dishes")
    public List<String> getDishes(@PathVariable String mealType) {
        return rgService.getDishesByMealType(mealType).keySet().stream().collect(Collectors.toList());
    }

    // Get the recipe for a selected dish
    @GetMapping("/meals/{mealType}/dishes/{dishName}")
    public String getRecipe(@PathVariable String mealType, @PathVariable String dishName) {
        return rgService.getRecipe(mealType, dishName);
    }
}

