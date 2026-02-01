package com.example.foodplanner.view.favorite.presenter;

import com.example.foodplanner.data.model.Meal;

public interface FavoritePresenter {
    void getFavorites();

    void removeFromFavorites(Meal meal);
}
