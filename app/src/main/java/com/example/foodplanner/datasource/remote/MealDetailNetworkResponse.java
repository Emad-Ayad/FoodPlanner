package com.example.foodplanner.datasource.remote;

import com.example.foodplanner.model.MealDetail;

public interface MealDetailNetworkResponse {
    void onSuccess(MealDetail meal);
    void onError(String message);
    void onInternetError(String message);
}
