package com.example.foodplanner.home.presenter;

import com.example.foodplanner.data.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.data.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.home.view.*;
import com.example.foodplanner.data.model.Meal;

import java.util.List;

public class HomePresenterImp implements HomePresenter {
    private HomeView view;
    private MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSource();

    private Meal meal;

    public HomePresenterImp(HomeView view) {
        this.view = view;
    }

    @Override
    public void getMealOfTheDay() {
        remoteDataSource.getMealOfTheDay(new MealsNetworkResponse() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (view != null && meals != null && !meals.isEmpty()) {
                    meal = meals.get(0);
                    view.showMealOfTheDay(meal);
                }
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

    @Override
    public void getQuickMeals() {
        remoteDataSource.getMealsByArea("Egyptian",new MealsNetworkResponse() {
            @Override
            public void onSuccess(List<Meal> meals) {
                view.showQuickMeals(meals);
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

    @Override
    public void mealOnClickLinstener() {
        view.navToMealDetails(meal.getId());
    }
}
