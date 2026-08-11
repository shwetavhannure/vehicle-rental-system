package com.example.bikerental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            SharedPreferences sp = SplashActivity.this.getSharedPreferences("UserSession", MODE_PRIVATE);
            boolean isLoggedIn = sp.getBoolean("isLoggedIn", false);
            String lastCity = sp.getString("lastCity", "");

            Intent intent;

            if (isLoggedIn) {

                if (!lastCity.isEmpty()) {

                    intent = new Intent(SplashActivity.this, VehicleActivity.class);

                    intent.putExtra("CITY_NAME", lastCity);
                } else {

                    intent = new Intent(SplashActivity.this, DashboardActivity.class);
                }
            } else {

                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();

        }, 2000);
    }
}