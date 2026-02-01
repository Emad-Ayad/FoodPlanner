package com.example.foodplanner.view.details.presenter;

import android.content.Context;

import com.example.foodplanner.data.datasource.remote.MealDetailNetworkResponse;
import com.example.foodplanner.data.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.view.details.view.DetailsView;
import com.example.foodplanner.data.model.MealDetail;
import com.example.foodplanner.data.repo.MealsRepo;

public class DetailsPresenterImp implements DetailsPresenter   {

    private DetailsView view;
    private MealsRemoteDataSource remoteDataSource;
    private MealsRepo repo;


    public DetailsPresenterImp(DetailsView view, Context context) {
        this.view = view;
        this.remoteDataSource = new MealsRemoteDataSource();
        this.repo = new MealsRepo(context);
    }

    public void getMealDetails(String mealId) {
        remoteDataSource.getMealDetails(mealId, new MealDetailNetworkResponse() {
            @Override
            public void onSuccess(MealDetail meal) {
                view.showMealDetails(meal);
                checkIfFav(meal.getId());
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

    @Override
    public void addToFav(Meal meal) {
        repo.insertFavMeal(meal)
                .subscribe(
                        () -> view.showFavAdded(),
                        e -> view.showErrorMessage(e.getMessage())
                );
    }

    @Override
    public void removeFromFav(Meal meal) {
        repo.deleteFavMeal(meal)
                .subscribe(
                        () -> view.showFavRemoved(),
                        e -> view.showErrorMessage(e.getMessage())
                );
    }

    @Override
    public void checkIfFav(String mealId) {
        repo.getFavMeals()
                .subscribe(
                        meals -> {
                            boolean isFav = false;
                            for (Meal m : meals) {
                                if (m.getId().equals(mealId)) {
                                    isFav = true;
                                    break;
                                }
                            }
                            view.updateFavState(isFav);
                        },
                        e -> view.showErrorMessage(e.getMessage())
                );
    }
}
