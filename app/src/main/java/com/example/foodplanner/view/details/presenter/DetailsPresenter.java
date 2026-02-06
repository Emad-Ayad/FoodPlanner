package com.example.foodplanner.view.details.presenter;

import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;

public interface DetailsPresenter {
    void getMealDetails(String mealId);
    void addToFav(Meal meal);
    void addToPlan(MealPlan plan);
    void removeFromFav(Meal meal);
    void checkIfFav(String mealId);
}
