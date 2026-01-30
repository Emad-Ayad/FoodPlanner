package com.example.foodplanner.home.presenter;

import com.example.foodplanner.data.model.MealPlan;

public interface HomePresenter {
    void getMealOfTheDay();
    void getQuickMeals();
    void mealOnClickLinstener();
    void addToPlan(MealPlan plan);
}
