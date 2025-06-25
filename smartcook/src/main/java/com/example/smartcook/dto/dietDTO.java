package com.example.smartcook.dto;

public class dietDTO {
    private double weight;
    private double height;
    private String dietType;

    // Getters and setters
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public String getDietType() { return dietType; }
    public void setDietType(String dietType) { this.dietType = dietType; }
}