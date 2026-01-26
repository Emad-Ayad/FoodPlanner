package com.example.foodplanner.search;

import android.widget.SearchView;

import com.example.foodplanner.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.model.Meal;

import java.util.List;

public class SearchPresenterImp implements SearchPresenter{

    private MealsRemoteDataSource remoteDataSource;

    private SearchedView view;

    public SearchPresenterImp(SearchedView view){
        this.view=view;
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
}
