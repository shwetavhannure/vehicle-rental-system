package com.example.bikerental;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VehicleActivity extends AppCompatActivity {

    TextView tvSelectedCity, tvStartDate, tvEndDate, tvUserWelcome;
    Button btnSearch;
    Calendar startCal, endCal;
    BottomNavigationView bottomNavigationView;
    boolean isStartDateSelected = false;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    private final List<String> supportedCities = Arrays.asList("Kolhapur", "Sangli");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        startCal = null;
        endCal = null;
        isStartDateSelected = false;

        tvSelectedCity = findViewById(R.id.tvSelectedCity);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvUserWelcome = findViewById(R.id.tvUserWelcome);
        btnSearch = findViewById(R.id.btnSearchVehicles);
        bottomNavigationView = findViewById(R.id.bottomNavigation);


        bottomNavigationView.setSelectedItemId(R.id.nav_rent);

        btnSearch.setEnabled(false);
        btnSearch.setAlpha(0.5f);

        SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userName = sp.getString("userName", "User");
        tvUserWelcome.setText("Hi, " + userName.toUpperCase() + "!");

        String city = getIntent().getStringExtra("CITY_NAME");
        if (city != null) {
            tvSelectedCity.setText(city);
            sp.edit().putString("lastCity", city).apply();
        } else {
            tvSelectedCity.setText(sp.getString("lastCity", "Explore"));
        }

        findViewById(R.id.rlStartDate).setOnClickListener(v -> showDatePicker(true));
        findViewById(R.id.rlEndDate).setOnClickListener(v -> {
            if (!isStartDateSelected) {
                Toast.makeText(this, "Select Start Date first", Toast.LENGTH_SHORT).show();
            } else {
                showDatePicker(false);
            }
        });

        btnSearch.setOnClickListener(v -> {
            if (startCal != null && endCal != null && endCal.getTimeInMillis() > startCal.getTimeInMillis()) {
                sp.edit().putString("fleet_city", tvSelectedCity.getText().toString())
                        .putString("fleet_start", tvStartDate.getText().toString())
                        .putString("fleet_end", tvEndDate.getText().toString())
                        .putBoolean("isSearching", true).apply();

                Intent intent = new Intent(VehicleActivity.this, FleetActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {
                btnSearch.setEnabled(false);
                btnSearch.setAlpha(0.5f);
                Toast.makeText(this, "End date must be greater than Start date", Toast.LENGTH_SHORT).show();
            }
        });

        setupBottomNavigation();
    }

    private void showDatePicker(boolean isStart) {
        Calendar currentCal = Calendar.getInstance();
        DatePickerDialog picker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth, 0, 0, 0);

            if (isStart) {
                startCal = selectedDate;
                tvStartDate.setText(sdf.format(startCal.getTime()));
                tvStartDate.setTextColor(Color.WHITE);
                isStartDateSelected = true;

                endCal = null;
                tvEndDate.setText("Ride End Date");
                tvEndDate.setTextColor(Color.parseColor("#B0BEC5"));
                btnSearch.setEnabled(false);
                btnSearch.setAlpha(0.5f);
            } else {
                endCal = selectedDate;
                tvEndDate.setText(sdf.format(endCal.getTime()));
                tvEndDate.setTextColor(Color.WHITE);
                validateDates();
            }
        }, currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH), currentCal.get(Calendar.DAY_OF_MONTH));

        if (isStart) {
            picker.getDatePicker().setMinDate(System.currentTimeMillis());
        } else if (startCal != null) {
            picker.getDatePicker().setMinDate(startCal.getTimeInMillis() + (24 * 60 * 60 * 1000));
        }
        picker.show();
    }

    private void validateDates() {
        if (startCal != null && endCal != null) {
            if (endCal.getTimeInMillis() > startCal.getTimeInMillis()) {
                btnSearch.setEnabled(true);
                btnSearch.setAlpha(1.0f);
            } else {
                btnSearch.setEnabled(false);
                btnSearch.setAlpha(0.5f);
            }
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();


            if (id == bottomNavigationView.getSelectedItemId()) {
                return false;
            }

            if (id == R.id.nav_bookings) {
                Intent intent = new Intent(this, BookingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return id == R.id.nav_rent;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        bottomNavigationView.getMenu().findItem(R.id.nav_rent).setChecked(true);
    }
}