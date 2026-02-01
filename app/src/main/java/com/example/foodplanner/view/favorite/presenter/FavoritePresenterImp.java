package com.example.foodplanner.view.favorite.presenter;

import android.content.Context;

import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.repo.MealsRepo;
import com.example.foodplanner.view.favorite.view.FavoriteView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenterImp implements FavoritePresenter {

    private FavoriteView view;
    private MealsRepo repo;

    public FavoritePresenterImp(FavoriteView view, Context context) {
        this.view = view;
        this.repo = new MealsRepo(context);
    }

    @Override
    public void getFavorites() {
        repo.getFavMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> view.showFavorites(meals),
                        error -> view.showError(error.getMessage()));
    }

    @Override
    public void removeFromFavorites(Meal meal) {
        repo.deleteFavMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {},
                        error -> view.showError(error.getMessage()));
    }
}
