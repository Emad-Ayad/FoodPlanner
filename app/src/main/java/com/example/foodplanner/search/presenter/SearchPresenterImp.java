package com.example.foodplanner.search.presenter;

import com.example.foodplanner.datasource.remote.AreasNetworkResponse;
import com.example.foodplanner.datasource.remote.CategoriesNetworkResponse;
import com.example.foodplanner.datasource.remote.IngredientsNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.search.view.SearchedView;

import java.util.List;

public class SearchPresenterImp implements SearchPresenter {

    private MealsRemoteDataSource remoteDataSource;

    private SearchedView view;

    public SearchPresenterImp(SearchedView view) {
        this.view = view;
        this.remoteDataSource = new MealsRemoteDataSource();
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
}
