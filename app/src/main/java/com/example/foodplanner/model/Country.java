package com.example.foodplanner.model;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("strArea")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
