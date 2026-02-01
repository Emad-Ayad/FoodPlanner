package com.example.foodplanner.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodplanner.firebase.*;
import com.example.foodplanner.*;
import com.example.foodplanner.auth.*;

import com.example.foodplanner.R;

public class SplashActivity extends AppCompatActivity {

    private AuthManger authManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        authManger = new AuthManger();

        new Handler(Looper.getMainLooper()).postDelayed(this::checkAuthStatus, 4000);
    }

    private void checkAuthStatus() {
        if (authManger.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

}