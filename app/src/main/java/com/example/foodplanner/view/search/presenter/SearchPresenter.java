package com.example.foodplanner.view.search.presenter;

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
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);


}
