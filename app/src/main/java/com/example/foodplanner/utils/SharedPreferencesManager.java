package com.example.foodplanner.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplanner.data.model.User;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "FoodPlannerPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_PHOTO = "user_photo";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences preferences;

    public SharedPreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserSession(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER_ID, user.getUid());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_NAME, user.getDisplayName());
        editor.putString(KEY_USER_PHOTO, user.getPhotoUrl());
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public User getUserSession() {
        if (!isUserLoggedIn()) {
            return null;
        }

        String uid = preferences.getString(KEY_USER_ID, null);
        String email = preferences.getString(KEY_USER_EMAIL, null);
        String name = preferences.getString(KEY_USER_NAME, null);
        String photo = preferences.getString(KEY_USER_PHOTO, null);

        return new User(uid, email, name, photo);
    }

    public boolean isUserLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearUserSession() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
