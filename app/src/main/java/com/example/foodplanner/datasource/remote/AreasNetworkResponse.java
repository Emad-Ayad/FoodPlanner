package com.example.foodplanner.datasource.remote;

import com.example.foodplanner.model.Country;

import java.util.List;

public interface AreasNetworkResponse {
    void onSuccess(List<Country> areas);

    void onError(String message);

    void onInternetError(String message);
}
