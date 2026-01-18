package com.example.foodplanner.datasource.remote;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface MealsNetworkResponse {
    void onSuccess(List<Meal> meals);
    void onError(String message);
    void onInternetError(String message);

}
