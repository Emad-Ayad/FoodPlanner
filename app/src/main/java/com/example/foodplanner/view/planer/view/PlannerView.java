package com.example.foodplanner.view.planer.view;

import com.example.foodplanner.data.model.MealPlan;
import java.util.List;

public interface PlannerView {
    void showPlan(List<MealPlan> plans);

    void showError(String message);
}
