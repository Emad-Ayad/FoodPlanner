package com.example.foodplanner.search.presenter;

import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;

public interface SearchPresenter {
    void getSearchedMeals(String search);

    void getMealsByArea(String area);

    void getMealsByIngredient(String ingredient);

    void getMealsByCategory(String category);

    void getCategories();

    void getAreas();

    void getIngredients();
    void addToPlan(MealPlan plan);
    void addToFav(Meal meal);

}
