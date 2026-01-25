package com.example.foodplanner.categorey;
import com.example.foodplanner.model.*;

import java.util.List;

public interface CategoryView {
    void showCategories(List<Category> categories);

    void showError(String message);
    void showInternetError(String message);
}
