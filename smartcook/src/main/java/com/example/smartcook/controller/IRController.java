package com.example.smartcook.controller;

import com.example.smartcook.service.IRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/international")
@CrossOrigin(origins = "http://localhost:3000") // local host of react
public class IRController {

    @Autowired
    private IRService irService;

    // Return list of all international dishes
    @GetMapping("/all")
    public List<String> getAllRecipes() {
        return irService.getAllInternationalRecipes();
    }

    // Return details of a single dish by name
    @GetMapping("/{dishName}")
    public Map<String, String> getRecipe(@PathVariable String dishName) {
        return irService.getRecipeByName(dishName);
    }
}