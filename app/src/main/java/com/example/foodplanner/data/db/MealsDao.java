package com.example.foodplanner.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;

@Dao
public interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertFav(Meal meal);

    @Query("SELECT * FROM meals")
    Observable<List<Meal>> getFavMeals();

    @Delete
    Completable deleteFav(Meal meal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealPlan(MealPlan mealPlan);
    @Query("SELECT * FROM meal_plans WHERE day = :day")
    Observable<List<MealPlan>> getMealPlanByDay(String day);
    @Query("SELECT * FROM meal_plans")
    Observable<List<MealPlan>> getAllMealPlans();
    @Delete
    Completable deleteMealPlan(MealPlan mealPlan);
}
