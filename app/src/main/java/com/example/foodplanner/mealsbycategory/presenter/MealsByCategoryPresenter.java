package com.example.foodplanner.mealsbycategory.presenter;

import com.example.foodplanner.data.model.MealPlan;

public interface MealsByCategoryPresenter {
    void getMealsByCategory(String category);

    void addToPlan(MealPlan plan);
}
