package com.example.foodplanner.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsDetailsResponse {
    @SerializedName("meals")
    private List<MealDetail> meals;

    public List<MealDetail> getMeals() {
        return meals;
    }
}
