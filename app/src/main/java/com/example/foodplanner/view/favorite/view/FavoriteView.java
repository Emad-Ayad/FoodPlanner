package com.example.foodplanner.view.favorite.view;

import com.example.foodplanner.data.model.Meal;
import java.util.List;

public interface FavoriteView {
    void showFavorites(List<Meal> meals);

    void showError(String message);
}
