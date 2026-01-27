package com.example.foodplanner.search.presenter;

public interface SearchPresenter {
    void getSearchedMeals(String search);

    void getMealsByArea(String area);

    void getMealsByIngredient(String ingredient);

    void getMealsByCategory(String category);

    void getCategories();

    void getAreas();

    void getIngredients();
}
