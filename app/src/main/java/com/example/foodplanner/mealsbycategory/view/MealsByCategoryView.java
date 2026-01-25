package com.example.foodplanner.mealsbycategory.view;

import com.example.foodplanner.model.Meal;
import java.util.List;

public interface MealsByCategoryView {
    void showMeals(List<Meal> meals);

    void showError(String message);

    void showInternetError(String message);
}
