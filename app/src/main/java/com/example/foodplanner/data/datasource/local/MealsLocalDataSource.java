package com.example.foodplanner.data.datasource.local;

import android.content.Context;

import com.example.foodplanner.data.db.MealsDao;
import com.example.foodplanner.data.db.AppDatabase;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;


import java.util.List;


import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSource {
    private MealsDao mealsDao;

    public MealsLocalDataSource(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        mealsDao = appDatabase.mealsDao();
    }

    public Observable<List<Meal>> getFavMeals() {
        return mealsDao.getFavMeals();
    }

    public Completable insertFavMeal(Meal meal) {
        return mealsDao.insertFav(meal);
    }

    public Completable deleteFavMeal(Meal meal) {
        return mealsDao.deleteFav(meal);
    }

    public Observable<List<MealPlan>> getAllMealPlans() {
        return mealsDao.getAllMealPlans();
    }

    public Observable<List<MealPlan>> getMealPlanByDay(String day) {
        return mealsDao.getMealPlanByDay(day);
    }

    public Completable insertMealPlan(MealPlan mealPlan) {
        return mealsDao.insertMealPlan(mealPlan);
    }

    public Completable deleteMealPlan(MealPlan mealPlan) {
        return mealsDao.deleteMealPlan(mealPlan);
    }





}
