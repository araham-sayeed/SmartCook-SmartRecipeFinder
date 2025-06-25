package com.example.smartcook.service;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class IRService {

    // fetching database
    private JSONObject loadRecipeData() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("InternationalRecipes.json")) {
            if (inputStream == null) {
                throw new RuntimeException("❌ File not found: InternationalRecipes.json");
            }
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONObject root = new JSONObject(tokener);
            return root.getJSONObject("InternationalDishes");
        } catch (Exception e) {
            throw new RuntimeException("❌ Error loading JSON: " + e.getMessage());
        }
    }

    // Get list of all dish names
    public List<String> getAllInternationalRecipes() {
        JSONObject dishes = loadRecipeData();
        return new ArrayList<>(dishes.keySet());
    }

    // Get details of a specific dish
    public Map<String, String> getRecipeByName(String dishName) {
        JSONObject dishes = loadRecipeData();

        if (!dishes.has(dishName)) {
            throw new RuntimeException("❌ Dish not found: " + dishName);
        }

        JSONObject details = dishes.getJSONObject(dishName);
        Map<String, String> recipeDetails = new HashMap<>();

        for (String key : details.keySet()) {
            recipeDetails.put(key, details.getString(key));
        }

        return recipeDetails;
    }
}