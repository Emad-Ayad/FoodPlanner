package com.example.foodplanner.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.utils.SharedPreferencesManager;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferencesManager prefsManager = new SharedPreferencesManager(this);
        if (prefsManager.isUserLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_auth);

    }
}