package com.example.foodplanner.datasource.remote;

import com.example.foodplanner.Network.Network;
import com.example.foodplanner.model.CategoryListResponse;
import com.example.foodplanner.model.MealDetail;
import com.example.foodplanner.model.MealsDetailsResponse;
import com.example.foodplanner.model.MealsResponse;

import java.io.IOException;

import com.example.foodplanner.model.CountriesResponse;
import com.example.foodplanner.model.IngredientsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDataSource {
    private MealsService mealsService;

    public MealsRemoteDataSource() {
        mealsService = Network.getInstance().mealsService;
    }

    public void getMealOfTheDay(MealsNetworkResponse callback) {
        mealsService.getMealOfTheDay().enqueue(getMeals(callback));
    }

    public void getMealsByCategory(String category, MealsNetworkResponse callback) {
        mealsService.getMealsByCategory(category).enqueue(getMeals(callback));
    }

    public void getMealsByIngredient(String ingredient, MealsNetworkResponse callback) {
        mealsService.getMealsByIngredient(ingredient).enqueue(getMeals(callback));
    }

    public void getMealsByArea(String Area, MealsNetworkResponse callback) {
        mealsService.getMealsByArea(Area).enqueue(getMeals(callback));
    }

    public void searchMeals(String query, MealsNetworkResponse callback) {
        mealsService.searchMeals(query).enqueue(getMeals(callback));
    }

    public void getMealDetails(String idMeal, MealDetailNetworkResponse callback) {
        mealsService.getMealDetails(idMeal).enqueue(new Callback<MealsDetailsResponse>() {
            @Override
            public void onResponse(Call<MealsDetailsResponse> call, Response<MealsDetailsResponse> response) {
                if (response.isSuccessful() && !response.body().getMeals().isEmpty()) {
                    MealDetail meal = response.body().getMeals().get(0);
                    callback.onSuccess(meal);
                }
            }

            @Override
            public void onFailure(Call<MealsDetailsResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.onInternetError("Network error");
                } else {
                    callback.onError(t.getMessage());
                }
            }
        });
    }

    public void getCategories(CategoriesNetworkResponse callback) {
        mealsService.getCategories().enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.onInternetError("Network error");
                } else {
                    callback.onError(t.getMessage());
                }
            }
        });
    }

    public void getAreas(AreasNetworkResponse callback) {
        mealsService.getAreas().enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getCountries());
                }
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.onInternetError("Network error");
                } else {
                    callback.onError(t.getMessage());
                }
            }
        });
    }

    public void getIngredients(IngredientsNetworkResponse callback) {
        mealsService.getIngredients().enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getIngredients());
                }
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.onInternetError("Network error");
                } else {
                    callback.onError(t.getMessage());
                }
            }
        });
    }

    private Callback<MealsResponse> getMeals(MealsNetworkResponse callback) {
        return new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.onInternetError("Network error");
                } else {
                    callback.onError(t.getMessage());
                }
            }
        };
    }
}
