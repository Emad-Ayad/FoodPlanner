package com.example.foodplanner.view.home.presenter;

import com.example.foodplanner.data.model.MealPlan;
import com.example.foodplanner.data.model.Meal;

public interface HomePresenter {
    void getMealOfTheDay();
    void getQuickMeals();
    void mealOnClickLinstener();
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
    void onMealOfTheDayFavClicked();

}
