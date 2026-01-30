package com.example.foodplanner.data.datasource.remote;

import com.example.foodplanner.data.model.Ingredient;

import java.util.List;

public interface IngredientsNetworkResponse {
    void onSuccess(List<Ingredient> ingredients);

    void onError(String message);

    void onInternetError(String message);
}
