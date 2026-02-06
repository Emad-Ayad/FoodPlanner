package com.example.foodplanner.view.mealsbycategory.presenter;

import com.example.foodplanner.data.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.data.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.view.mealsbycategory.view.MealsByCategoryView;
import com.example.foodplanner.data.model.Meal;

import java.util.List;

import android.content.Context;
import com.example.foodplanner.data.repo.MealsRepo;
import com.example.foodplanner.data.model.MealPlan;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MealsByCategoryPresenterImp implements MealsByCategoryPresenter {

    private MealsByCategoryView view;
    private MealsRemoteDataSource remoteDataSource;
    private MealsRepo repo;

    public MealsByCategoryPresenterImp(MealsByCategoryView view, Context context) {
        this.view = view;
        this.remoteDataSource = new MealsRemoteDataSource();
        this.repo = new MealsRepo(context);
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

    @Override
    public void removeFromFav(Meal meal) {
        repo.deleteFavMeal(meal)
                .subscribe(
                        () -> {},
                        e -> view.showError(e.getMessage())
                );
    }

    @Override
    public void getMealsByCategory(String category) {
        remoteDataSource.getMealsByCategory(category, new MealsNetworkResponse() {
            @Override
            public void onSuccess(List<Meal> meals) {
                repo.getFavMeals()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(favMeals -> {

                            for (Meal meal : meals) {
                                for (Meal fav : favMeals) {
                                    if (meal.getId().equals(fav.getId())) {
                                        meal.setFav(true);
                                        break;
                                    }
                                }
                            }

                            view.showMeals(meals);
                        }, e -> view.showError(e.getMessage()));
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
