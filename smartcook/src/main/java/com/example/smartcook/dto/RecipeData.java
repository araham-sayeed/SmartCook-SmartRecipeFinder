package com.example.smartcook.dto;

import java.util.List;

public class RecipeData {
    private String mainIngredient;
    private List<String> addOns;

    public RecipeData() {}

    public RecipeData(String mainIngredient, List<String> addOns) {
        this.mainIngredient = mainIngredient;
        this.addOns = addOns;
    }

    public String getMainIngredient() {
        return mainIngredient;
    }

    public void setMainIngredient(String mainIngredient) {
        this.mainIngredient = mainIngredient;
    }

    public List<String> getAddOns() {
        return addOns;
    }

    public void setAddOns(List<String> addOns) {
        this.addOns = addOns;
    }
}