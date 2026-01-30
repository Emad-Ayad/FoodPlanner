package com.example.foodplanner.data.datasource.remote;

import com.example.foodplanner.data.model.Country;

import java.util.List;

public interface AreasNetworkResponse {
    void onSuccess(List<Country> areas);

    void onError(String message);

    void onInternetError(String message);
}
