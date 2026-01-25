package com.example.foodplanner.mealsbycategory.presenter;

import com.example.foodplanner.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.mealsbycategory.view.MealsByCategoryView;
import com.example.foodplanner.model.Meal;

import java.util.List;

public class MealsByCategoryPresenterImp implements MealsByCategoryPresenter {

    private MealsByCategoryView view;
    private MealsRemoteDataSource remoteDataSource;

    public MealsByCategoryPresenterImp(MealsByCategoryView view) {
        this.view = view;
        this.remoteDataSource = new MealsRemoteDataSource();
    }

    @Override
    public void getMealsByCategory(String category) {
        remoteDataSource.getMealsByCategory(category, new MealsNetworkResponse() {
            @Override
            public void onSuccess(List<Meal> meals) {
                view.showMeals(meals);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }

            @Override
            public void onInternetError(String message) {
                view.showInternetError(message);

            }
        });
    }
}
