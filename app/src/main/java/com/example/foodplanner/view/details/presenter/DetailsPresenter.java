package com.example.foodplanner.view.details.presenter;

import com.example.foodplanner.data.model.Meal;

public interface DetailsPresenter {
    void getMealDetails(String mealId);
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
    void checkIfFav(String mealId);
}
