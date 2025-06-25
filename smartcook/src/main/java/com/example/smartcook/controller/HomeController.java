package com.example.smartcook.controller;

import com.example.smartcook.dto.RecipeData;
import com.example.smartcook.service.SmartRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
// for connecting with react
@RestController
@RequestMapping("/api/smart-recipe")
@CrossOrigin(origins = "http://localhost:3000") // local host of react
public class HomeController {

    @Autowired
    private SmartRecipeService smartRecipeService;

    @GetMapping("/ingredients")
    public List<String> getAllMainIngredients() {
        return smartRecipeService.getMainIngredients();
    }

    @GetMapping("/addons/{ingredient}")
    public List<String> getAddOnsForMainIngredient(@PathVariable String ingredient) {
        return smartRecipeService.getAddOns(ingredient);
    }

    @PostMapping("/find")
    public Map<String, Object> findSmartRecipe(@RequestBody RecipeData dto) {
        return smartRecipeService.findRecipe(dto);
    }
}