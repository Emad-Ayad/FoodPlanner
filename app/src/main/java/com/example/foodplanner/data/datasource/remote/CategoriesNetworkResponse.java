package com.example.foodplanner.data.datasource.remote;

import com.example.foodplanner.data.model.Category;

import java.util.List;

public interface CategoriesNetworkResponse {
    void onSuccess(List<Category> categories);
    void onError(String message);
    void onInternetError(String message);
}
