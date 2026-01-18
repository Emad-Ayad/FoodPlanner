package com.example.foodplanner.Network;

import com.example.foodplanner.datasource.remote.MealsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    public MealsService mealsService;
    private static Network instance= null;

    private Network (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mealsService =retrofit.create(MealsService.class);
    }

    public static Network getInstance(){
        if (instance == null){
            instance = new Network();
        }
        return instance;
    }
}
