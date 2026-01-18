package com.example.foodplanner.datasource.remote;

import com.example.foodplanner.model.Category;

import java.util.List;

public interface CategoriesNetworkResponse {
    void onSuccess(List<Category> categories);
    void onError(String message);
    void onInternetError(String message);
}
