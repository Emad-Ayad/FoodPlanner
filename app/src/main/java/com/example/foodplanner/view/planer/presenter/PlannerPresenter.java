package com.example.foodplanner.view.planer.presenter;

import com.example.foodplanner.data.model.MealPlan;

public interface PlannerPresenter {
    void getMealPlan();

    void removeFromPlan(MealPlan mealPlan);
}
