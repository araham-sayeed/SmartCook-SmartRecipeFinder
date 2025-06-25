package com.example.smartcook.controller;

import com.example.smartcook.dto.dietDTO;
import com.example.smartcook.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
// for connecting with react
@RestController
@RequestMapping("/api/diet")
@CrossOrigin(origins = "http://localhost:3000") // local host of react
public class DietController {

    @Autowired
    private DietService dietService;

    @PostMapping("/plan")
    public Map<String, Map<String, Object>> getDietPlan(@RequestBody dietDTO input) throws Exception {
        return dietService.generateMealPlan(input.getWeight(), input.getHeight(), input.getDietType());
    }
}