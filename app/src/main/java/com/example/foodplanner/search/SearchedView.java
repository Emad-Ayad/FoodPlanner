package com.example.foodplanner.search;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface SearchedView {
    void showMeals(List<Meal> meals);

    void showError(String message);

    void showInternetError(String message);
}
