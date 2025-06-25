package com.example.smartcook.service;

import com.example.smartcook.dto.RecipeData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class SmartRecipeService {
    // recipe function
    public Map<String, Object> findRecipe(RecipeData dto) {
        String mainInput = dto.getMainIngredient().trim().toLowerCase();
        List<String> lowerInput = new ArrayList<>();

        for (String ing : dto.getAddOns()) {
            lowerInput.add(ing.trim().toLowerCase());
        }

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("SmartRecipe.json");
            if (inputStream == null)
                throw new RuntimeException("SmartRecipe.json not found");

            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String jsonString = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject jsonRoot = new JSONObject(jsonString);
            JSONObject mainObject = jsonRoot.getJSONObject("main");

            if (!mainObject.has(mainInput)) {
                return Map.of(
                        "status", "not_found",
                        "data", null,
                        "message", "❌ No recipe found for the selected main ingredient.");
            }

            JSONArray recipes = mainObject.getJSONArray(mainInput);

            for (int i = 0; i < recipes.length(); i++) {
                JSONObject recipe = recipes.getJSONObject(i);
                JSONArray addOns = recipe.getJSONArray("addOns");

                List<String> addOnList = new ArrayList<>();
                for (int j = 0; j < addOns.length(); j++) {
                    addOnList.add(addOns.getString(j).toLowerCase());
                }

                boolean addOnMatch = lowerInput.stream().anyMatch(userAddOn ->
                        addOnList.stream().anyMatch(jsonAddOn ->
                                jsonAddOn.replaceAll("\\s+", "").contains(userAddOn.replaceAll("\\s+", ""))
                        )
                );

                System.out.println("🔍 Checking recipe: " + recipe.getString("dishName"));
                System.out.println("🔸 Add-ons from JSON: " + addOnList);
                System.out.println("🔹 User input: " + lowerInput);
                System.out.println("✅ Match found: " + addOnMatch);

                if (addOnMatch) {
                    Map<String, Object> recipeData = new HashMap<>();
                    recipeData.put("dishName", recipe.getString("dishName"));
                    recipeData.put("main", mainInput);
                    recipeData.put("addOns", addOnList);
                    recipeData.put("steps", recipe.getJSONArray("steps").toList());
                    recipeData.put("image", recipe.optString("image", ""));

                    return Map.of(
                            "status", "success",
                            "data", recipeData,
                            "message", "✅ Recipe found successfully!");
                }
            }

            return Map.of(
                    "status", "not_found",
                    "data", null,
                    "message", "❌ No matching recipe found for given add-ons.");

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of(
                    "status", "error",
                    "data", null,
                    "message", "⚠️ Something went wrong while fetching the recipe.");
        }
    }

    public List<String> getMainIngredients() {
        List<String> mainIngredients = new ArrayList<>();
        // fetching database
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("SmartRecipe.json")) {
            if (inputStream == null)
                throw new RuntimeException("SmartRecipe.json not found");

            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String jsonString = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject jsonRoot = new JSONObject(jsonString);
            JSONObject mainObject = jsonRoot.getJSONObject("main");

            Iterator<String> keys = mainObject.keys();
            while (keys.hasNext()) {
                mainIngredients.add(keys.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mainIngredients;
    }

    public List<String> getAddOns(String mainIngredient) {
        List<String> addOns = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("SmartRecipe.json")) {
            if (inputStream == null)
                throw new RuntimeException("SmartRecipe.json not found");

            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String jsonString = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject jsonRoot = new JSONObject(jsonString);
            JSONObject mainObject = jsonRoot.getJSONObject("main");

            if (!mainObject.has(mainIngredient.toLowerCase())) {
                return addOns;
            }

            JSONArray recipeArray = mainObject.getJSONArray(mainIngredient.toLowerCase());

            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipe = recipeArray.getJSONObject(i);
                JSONArray jsonAddOns = recipe.getJSONArray("addOns");

                for (int j = 0; j < jsonAddOns.length(); j++) {
                    String addOn = jsonAddOns.getString(j);
                    if (!addOns.contains(addOn)) {
                        addOns.add(addOn);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return addOns;
    }
}