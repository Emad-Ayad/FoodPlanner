package com.example.foodplanner.details.view;

import com.example.foodplanner.model.*;

public interface DetailsView {
    void showMealDetails(MealDetail meal);

    void showErrorMessage(String error);
}
