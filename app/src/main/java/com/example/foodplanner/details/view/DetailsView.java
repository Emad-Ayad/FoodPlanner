package com.example.foodplanner.details.view;

import com.example.foodplanner.data.model.MealDetail;

public interface DetailsView {
    void showMealDetails(MealDetail meal);
    void showFavAdded();
    void showFavRemoved();
    void updateFavState(boolean isFav);
    void showErrorMessage(String error);
}
