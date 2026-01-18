package com.example.foodplanner.datasource.remote;

import com.example.foodplanner.Network.Network;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealsResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDataSource {
    private MealsService mealsService;

    public MealsRemoteDataSource(){
        mealsService = Network.getInstance().mealsService;
    }

    public void getMealOfTheDay(MealsNetworkResponse callback){
        mealsService.getMealOfTheDay().enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if(response.isSuccessful()){
                    List<Meal> meals= response.body().getMeals();
                    callback.onSuccess(meals);
                }
            }
            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                if(t instanceof IOException) {
                    callback.onInternetError("Error Network, Please Check your Network");
                } else {
                    callback.onError(t.getMessage());
                }
            }
        });
    }
}

