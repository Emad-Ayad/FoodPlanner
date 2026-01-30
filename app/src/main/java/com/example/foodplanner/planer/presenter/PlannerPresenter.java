package com.example.foodplanner.planer.presenter;

import com.example.foodplanner.data.model.MealPlan;

public interface PlannerPresenter {
    void getMealPlan();

    void removeFromPlan(MealPlan mealPlan);
}
