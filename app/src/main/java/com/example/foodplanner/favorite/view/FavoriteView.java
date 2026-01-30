package com.example.foodplanner.favorite.view;

import com.example.foodplanner.data.model.Meal;
import java.util.List;

public interface FavoriteView {
    void showFavorites(List<Meal> meals);

    void showError(String message);
}
