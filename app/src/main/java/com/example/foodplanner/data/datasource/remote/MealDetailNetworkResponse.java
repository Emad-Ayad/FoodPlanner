package com.example.foodplanner.data.datasource.remote;

import com.example.foodplanner.data.model.MealDetail;

public interface MealDetailNetworkResponse {
    void onSuccess(MealDetail meal);
    void onError(String message);
    void onInternetError(String message);
}
