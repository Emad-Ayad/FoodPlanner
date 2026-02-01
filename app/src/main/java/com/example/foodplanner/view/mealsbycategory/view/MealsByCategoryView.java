package com.example.foodplanner.view.mealsbycategory.view;

import com.example.foodplanner.data.model.Meal;
import java.util.List;

public interface MealsByCategoryView {
    void showMeals(List<Meal> meals);

    void showError(String message);

    void showInternetError(String message);
}
