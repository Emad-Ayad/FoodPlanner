package com.example.foodplanner.data.datasource.remote;

import com.example.foodplanner.data.model.Meal;

import java.util.List;

public interface MealsNetworkResponse {
    void onSuccess(List<Meal> meals);
    void onError(String message);
    void onInternetError(String message);

}
