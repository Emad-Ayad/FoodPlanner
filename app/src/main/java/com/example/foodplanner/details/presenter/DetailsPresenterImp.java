package com.example.foodplanner.details.presenter;

import com.example.foodplanner.datasource.remote.MealDetailNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.details.view.DetailsView;
import com.example.foodplanner.model.MealDetail;

public class DetailsPresenterImp implements DetailsPresenter   {

    private DetailsView view;
    private MealsRemoteDataSource remoteDataSource;

    public DetailsPresenterImp(DetailsView view) {
        this.view = view;
        this.remoteDataSource = new MealsRemoteDataSource();
    }


    public void getMealDetails(String mealId) {
        remoteDataSource.getMealDetails(mealId, new MealDetailNetworkResponse() {
            @Override
            public void onSuccess(MealDetail meal) {
                view.showMealDetails(meal);
            }

            @Override
            public void onError(String message) {
                view.showErrorMessage(message);
            }

            @Override
            public void onInternetError(String message) {
                view.showErrorMessage(message);
            }
        });
    }
}
