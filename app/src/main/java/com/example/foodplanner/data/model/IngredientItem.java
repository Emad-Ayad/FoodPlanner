package com.example.foodplanner.data.model;

public class IngredientItem {
    private String name;
    private String measure;

    public IngredientItem(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public String getImageUrl() {
        return "https://www.themealdb.com/images/ingredients/"
                + name.toLowerCase().replace(" ", "_")
                + ".png";
    }
}
