package com.example.foodplanner.details.view;

import com.example.foodplanner.data.model.MealDetail;

public interface DetailsView {
    void showMealDetails(MealDetail meal);

    void showErrorMessage(String error);
}
