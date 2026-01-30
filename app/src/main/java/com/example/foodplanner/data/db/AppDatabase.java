package com.example.foodplanner.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;
@Database(entities = {Meal.class,MealPlan.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MealsDao mealsDao();

    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            "mealsdb")
                    .build();

        }
        return instance;
    }

}
