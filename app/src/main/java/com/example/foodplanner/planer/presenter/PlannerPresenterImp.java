package com.example.foodplanner.planer.presenter;

import android.content.Context;

import com.example.foodplanner.data.model.MealPlan;
import com.example.foodplanner.data.repo.MealsRepo;
import com.example.foodplanner.planer.view.PlannerView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannerPresenterImp implements PlannerPresenter {

    private PlannerView view;
    private MealsRepo repo;

    public PlannerPresenterImp(PlannerView view, Context context) {
        this.view = view;
        this.repo = new MealsRepo(context);
    }

    @Override
    public void getMealPlan() {
        repo.getAllMealPlans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        plans -> view.showPlan(plans),
                        error -> view.showError(error.getMessage()));
    }

    @Override
    public void removeFromPlan(MealPlan mealPlan) {
        repo.deleteMealPlan(mealPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> getMealPlan(),
                        error -> view.showError(error.getMessage()));
    }
}
