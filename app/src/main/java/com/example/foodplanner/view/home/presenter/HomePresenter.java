package com.example.foodplanner.view.home.presenter;

import com.example.foodplanner.data.model.MealPlan;
import com.example.foodplanner.data.model.Meal;

public interface HomePresenter {
    void getMealOfTheDay();
    void getQuickMeals();
    void mealOnClickLinstener();
    void addToPlan(MealPlan plan);
    void addToFav(Meal meal);
}
