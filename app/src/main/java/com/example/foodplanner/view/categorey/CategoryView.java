package com.example.foodplanner.view.categorey;
import com.example.foodplanner.data.model.Category;

import java.util.List;

public interface CategoryView {
    void showCategories(List<Category> categories);

    void showError(String message);
    void showInternetError(String message);
}
