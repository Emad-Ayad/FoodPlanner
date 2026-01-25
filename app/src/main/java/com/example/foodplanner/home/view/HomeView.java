package com.example.foodplanner.home.view;

import com.example.foodplanner.model.*;

import java.util.List;

public interface HomeView {
    void showMealOfTheDay(Meal meal);
    void showQuickMeals(List<Meal> meals);
    void showError(String message);
    void showInternetError(String message);
}
