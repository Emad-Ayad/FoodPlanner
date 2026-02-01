package com.example.foodplanner.view.search.presenter;

import android.content.Context;

import com.example.foodplanner.data.datasource.remote.AreasNetworkResponse;
import com.example.foodplanner.data.datasource.remote.CategoriesNetworkResponse;
import com.example.foodplanner.data.datasource.remote.IngredientsNetworkResponse;
import com.example.foodplanner.data.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.data.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.data.model.Category;
import com.example.foodplanner.data.model.Country;
import com.example.foodplanner.data.model.Ingredient;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;
import com.example.foodplanner.data.repo.MealsRepo;
import com.example.foodplanner.view.search.view.SearchedView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {

    private MealsRemoteDataSource remoteDataSource;

    private SearchedView view;
    private MealsRepo repo;


    public SearchPresenterImp(SearchedView view, Context context) {
        this.view = view;
        this.remoteDataSource = new MealsRemoteDataSource();
        this.repo = new MealsRepo(context);
    }

    @Override
    public void getSearchedMeals(String search) {
        remoteDataSource.searchMeals(search, new MealsNetworkResponse() {
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

    @Override
    public void getMealsByArea(String area) {
        remoteDataSource.getMealsByArea(area, new MealsNetworkResponse() {
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

    @Override
    public void getMealsByIngredient(String ingredient) {
        remoteDataSource.getMealsByIngredient(ingredient, new MealsNetworkResponse() {
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

    @Override
    public void getCategories() {
        remoteDataSource.getCategories(new CategoriesNetworkResponse() {
            @Override
            public void onSuccess(List<Category> categories) {
                view.showCategories(categories);
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
    public void getAreas() {
        remoteDataSource.getAreas(new AreasNetworkResponse() {
            @Override
            public void onSuccess(List<Country> areas) {
                view.showAreas(areas);
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
    public void getIngredients() {
        remoteDataSource.getIngredients(new IngredientsNetworkResponse() {
            @Override
            public void onSuccess(List<Ingredient> ingredients) {
                view.showIngredients(ingredients);
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
