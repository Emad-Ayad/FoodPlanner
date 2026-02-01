package com.example.foodplanner.view.home.presenter;

import android.content.Context;

import com.example.foodplanner.data.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.data.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.data.model.MealPlan;
import com.example.foodplanner.data.repo.MealsRepo;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.view.home.view.HomeView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImp implements HomePresenter {
    private HomeView view;
    private MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSource();
    private MealsRepo repo;
    private Meal meal;

    public HomePresenterImp(HomeView view, Context context) {
        this.view = view;
        this.repo = new MealsRepo(context);
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

    @Override
    public void addToPlan(MealPlan plan) {
        repo.insertMealPlan(plan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {},
                        error -> view.showError(error.getMessage()));
    }

    @Override
    public void addToFav(Meal meal) {
        repo.insertFavMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {},
                        error -> view.showError(error.getMessage()));

    }

}
