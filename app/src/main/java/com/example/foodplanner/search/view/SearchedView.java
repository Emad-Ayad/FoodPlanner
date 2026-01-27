package com.example.foodplanner.search.view;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.Meal;

import java.util.List;

public interface SearchedView {
    void showMeals(List<Meal> meals);

    void showCategories(List<Category> categories);

    void showAreas(List<Country> countries);

    void showIngredients(List<Ingredient> ingredients);

    void showError(String message);

    void showInternetError(String message);
}
