package com.example.smartcook.service;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class RGService {

    private final JSONObject recipes;

    public RGService() {
        this.recipes = loadRecipes();
    }

    // fetching database
    private JSONObject loadRecipes() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("recipes.json");

            if (inputStream == null) {
                throw new RuntimeException("RG.json not found in resources!");
            }

            return new JSONObject(new JSONTokener(inputStream));

        } catch (Exception e) {
            throw new RuntimeException("Error loading RG.json: " + e.getMessage());
        }
    }

    public JSONObject getAllMealTypes() {
        return recipes;
    }

    public JSONObject getDishesByMealType(String mealType) {
        if (!recipes.has(mealType)) {
            throw new RuntimeException("Meal type not found: " + mealType);
        }
        return recipes.getJSONObject(mealType);
    }

    public String getRecipe(String mealType, String dishName) {
        if (!recipes.has(mealType)) {
            throw new RuntimeException("Meal type not found: " + mealType);
        }

        JSONObject dishes = recipes.getJSONObject(mealType);
        return dishes.optString(dishName, "Recipe not found.");
    }
}

