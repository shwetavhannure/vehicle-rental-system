package com.example.bikerental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FleetActivity extends AppCompatActivity {

    private RecyclerView rvFleet;
    private VehicleAdapter adapter;
    private ArrayList<VehicleModel> allVehicles;
    private TextView tvCount, tvCity, tvStart, tvEnd;
    private Button btnAll, btnBikes, btnScooters;
    private BottomNavigationView bottomNavigationView;
    private int daysBetween = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet);


        rvFleet = findViewById(R.id.rvFleet);
        tvCount = findViewById(R.id.tvCount);
        tvCity = findViewById(R.id.tvFleetCity);
        tvStart = findViewById(R.id.tvFleetStart);
        tvEnd = findViewById(R.id.tvFleetEnd);
        btnAll = findViewById(R.id.btnAll);
        btnBikes = findViewById(R.id.btnBikes);
        btnScooters = findViewById(R.id.btnScooters);
        bottomNavigationView = findViewById(R.id.bottomNavigation);


        bottomNavigationView.setSelectedItemId(R.id.nav_rent);


        rvFleet.setLayoutManager(new LinearLayoutManager(this));


        loadSearchData();


        btnAll.setOnClickListener(v -> filterList("all"));
        btnBikes.setOnClickListener(v -> filterList("bike"));
        btnScooters.setOnClickListener(v -> filterList("scooter"));

        setupBottomNavigation();
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
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    private void updateButtonStyles(String type) {
        btnAll.setBackgroundResource(type.equals("all") ? R.drawable.btn_bg : R.drawable.edit_bg);
        btnBikes.setBackgroundResource(type.equals("bike") ? R.drawable.btn_bg : R.drawable.edit_bg);
        btnScooters.setBackgroundResource(type.equals("scooter") ? R.drawable.btn_bg : R.drawable.edit_bg);
    }

    private void loadSearchData() {
        SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);
        String city = sp.getString("fleet_city", "Kolhapur");

        tvCity.setText(city);
        tvStart.setText(sp.getString("fleet_start", ""));
        tvEnd.setText(sp.getString("fleet_end", ""));

        calculateDays(sp.getString("fleet_start", ""), sp.getString("fleet_end", ""));
        refreshVehicleList();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        bottomNavigationView.getMenu().findItem(R.id.nav_rent).setChecked(true);
        refreshVehicleList();
    }

    public void refreshVehicleList() {
        // Default to "all" behavior on refresh
        filterList("all");
    }

    private void calculateDays(String start, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date d1 = sdf.parse(start);
            Date d2 = sdf.parse(end);
            if (d1 != null && d2 != null) {
                daysBetween = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
                if (daysBetween <= 0) daysBetween = 1;
            }
        } catch (Exception e) { daysBetween = 1; }
    }

    private void filterList(String type) {

        DatabaseHelper db = new DatabaseHelper(this);
        SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);
        String selectedCity = sp.getString("fleet_city", "Kolhapur");
        allVehicles = db.getVehiclesByCity(selectedCity);


        ArrayList<VehicleModel> displayList = new ArrayList<>();
        if (type.equals("all")) {
            displayList.addAll(allVehicles);
        } else {
            for (VehicleModel v : allVehicles) {
                if (v.getType().equalsIgnoreCase(type)) {
                    displayList.add(v);
                }
            }
        }


        if (adapter == null) {
            adapter = new VehicleAdapter(displayList, daysBetween);
            rvFleet.setAdapter(adapter);
        } else {
            adapter.updateList(displayList);
        }

        updateButtonStyles(type);
        updateUI();
    }

    private void updateUI() {
        tvCount.setText("");
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, VehicleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}