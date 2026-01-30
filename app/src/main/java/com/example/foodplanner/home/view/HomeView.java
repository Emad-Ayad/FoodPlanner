package com.example.foodplanner.home.view;

import com.example.foodplanner.data.model.Meal;
import java.util.List;

public interface HomeView {
    void showMealOfTheDay(Meal meal);
    void showQuickMeals(List<Meal> meals);
    void navToMealDetails(String mealId);
    void showError(String message);
    void showInternetError(String message);
}
