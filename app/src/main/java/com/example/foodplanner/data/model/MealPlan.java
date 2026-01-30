package com.example.foodplanner.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "meal_plans")
public class MealPlan {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mealId;
    private String day;
    private String name;
    private String imageUrl;
    private String area;
    private String category;
    public MealPlan(String mealId, String day, String name, String imageUrl, String area, String category) {
        this.mealId = mealId;
        this.day = day;
        this.name = name;
        this.imageUrl = imageUrl;
        this.area = area;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}