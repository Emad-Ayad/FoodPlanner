package com.example.foodplanner.datasource.remote;

import com.example.foodplanner.model.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {
    @GET("random.php")
    Call<MealsResponse> getMealOfTheDay();

    @GET("filter.php")
    Call<MealsResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealsResponse> getMealsByArea(@Query("a") String area);

    @GET("lookup.php")
    Call<MealsDetailsResponse> getMealDetails(@Query("i") String idMeal);

    @GET("categories.php")
    Call<CategoryListResponse> getCategories();

    @GET("list.php?a=list")
    Call<CountriesResponse> getAreas();

    @GET("list.php?i=list")
    Call<IngredientsResponse> getIngredients();

    @GET("search.php")
    Call<MealsResponse> searchMeals(@Query("s") String query);
}
