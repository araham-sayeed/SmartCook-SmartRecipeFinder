package com.example.smartcook.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class DietService {

    public Map<String, Map<String, Object>> generateMealPlan(double weight, double height, String dietType) throws Exception {
        double bmi = weight / ((height / 100) * (height / 100));

        String goal;
        if (bmi < 18.5) goal = "Bulking";
        else if (bmi < 25) goal = "Maintenance";
        else goal = "Weight Loss";

        InputStream is = getClass().getClassLoader().getResourceAsStream("DietPlanner.json");
        if (is == null) throw new Exception("DietPlanner.json not found");

        String jsonText = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(jsonText);

        String cleanedType = dietType.equalsIgnoreCase("veg") ? "Veg" : "Non-Veg";

        JSONObject daysPlan = json.getJSONObject(cleanedType).getJSONObject(goal);
        Iterator<String> days = daysPlan.keys();

        Map<String, Map<String, Object>> result = new LinkedHashMap<>();
        while (days.hasNext()) {
            String day = days.next();
            JSONObject meals = daysPlan.getJSONObject(day);

            Map<String, Object> mealDetails = new HashMap<>();
            mealDetails.put("Breakfast", meals.getJSONObject("Breakfast").toMap());
            mealDetails.put("Lunch", meals.getJSONObject("Lunch").toMap());
            mealDetails.put("Dinner", meals.getJSONObject("Dinner").toMap());

            result.put(day, mealDetails);
        }

        return result;
    }
}