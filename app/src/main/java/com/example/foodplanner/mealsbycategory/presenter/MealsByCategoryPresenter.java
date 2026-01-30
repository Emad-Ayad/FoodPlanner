package com.example.foodplanner.mealsbycategory.presenter;

import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;

public interface MealsByCategoryPresenter {
    void getMealsByCategory(String category);

    void addToPlan(MealPlan plan);
    void addToFav(Meal meal);

}
