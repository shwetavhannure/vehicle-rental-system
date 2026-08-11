package com.example.bikerental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView tvName, tvPhone, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tvName = findViewById(R.id.tvProfileName);
        tvPhone = findViewById(R.id.tvProfilePhone);
        tvEmail = findViewById(R.id.tvProfileEmail);


        loadUserData();


        Button btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);
                sp.edit().clear().apply();


                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();


                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }, 2000);
        });


        setupBottomNavigation();
    }

    private void loadUserData() {
        SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);


        String fName = sp.getString("etFirstName", "");
        String lName = sp.getString("etLastName", "");
        String phone = sp.getString("etPhone", "Not provided");
        String email = sp.getString("etEmail", "Not provided");


        String fullName = (fName + " " + lName).trim();


        tvName.setText(fullName.isEmpty() ? "User Name" : fullName);
        tvPhone.setText(phone);
        tvEmail.setText(email);
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == bottomNavigationView.getSelectedItemId()) {
                return false;
            }

            if (id == R.id.nav_rent) {
                startActivity(new Intent(this, VehicleActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_bookings) {
                startActivity(new Intent(this, BookingsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }

            return id == R.id.nav_profile;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (bottomNavigationView != null) {
//            bottomNavigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);
//        }
    }
}