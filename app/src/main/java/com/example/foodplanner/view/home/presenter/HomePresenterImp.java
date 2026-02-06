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

                            view.showQuickMeals(meals);
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

    @Override
    public void mealOnClickLinstener() {
        view.navToMealDetails(meal.getId());
    }


    @Override
    public void addToFav(Meal meal) {
        repo.insertFavMeal(meal)
                .subscribe(
                        () -> {},
                        e -> view.showError(e.getMessage())
                );

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
    public void onMealOfTheDayFavClicked() {

        if (meal.isFav()) {
            meal.setFav(false);
            repo.deleteFavMeal(meal)
                    .subscribe(
                            () -> view.updateFavIcon(false),
                            e -> view.showError(e.getMessage())
                    );
        } else {
            meal.setFav(true);
            repo.insertFavMeal(meal)
                    .subscribe(
                            () -> view.updateFavIcon(true),
                            e -> view.showError(e.getMessage())
                    );
        }
    }


}

