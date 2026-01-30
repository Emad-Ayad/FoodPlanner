package com.example.foodplanner.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "meals")
public class Meal {
    @SerializedName("idMeal")
    @PrimaryKey
    @NonNull
    private String id ;
    @SerializedName("strMeal")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("strArea")
    @ColumnInfo(name = "area")
    private String area;
    @SerializedName("strMealThumb")
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
