package com.example.bikerental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class BookingsActivity extends AppCompatActivity {

    private ListView lvBookings;
    private TextView tvNoBooking;
    private BottomNavigationView bottomNavigationView;
    private CardView llCancellationMsg;
    private ImageView ivCloseMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        lvBookings = findViewById(R.id.lvBookings);
        tvNoBooking = findViewById(R.id.tvNoBooking);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        llCancellationMsg = findViewById(R.id.llCancellationMsg);
        ivCloseMsg = findViewById(R.id.ivCloseMsg);


        bottomNavigationView.setSelectedItemId(R.id.nav_bookings);

        ivCloseMsg.setOnClickListener(v -> llCancellationMsg.setVisibility(View.GONE));

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();


            if (id == bottomNavigationView.getSelectedItemId()) {
                return false;
            }

            if (id == R.id.nav_rent) {
                navigateToRent();
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

    private void navigateToRent() {
        SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isSearching = sp.getBoolean("isSearching", false);


        Intent intent = new Intent(this, isSearching ? FleetActivity.class : VehicleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        bottomNavigationView.getMenu().findItem(R.id.nav_bookings).setChecked(true);


        if (AppConfig.showCancellationMessage) {
            llCancellationMsg.setVisibility(View.VISIBLE);
            AppConfig.showCancellationMessage = false;
        } else {
            llCancellationMsg.setVisibility(View.GONE);
        }

        loadBookings();
    }

    private void loadBookings() {
        SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userPhone = sp.getString("etPhone", "");

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<BookingModel> list = dbHelper.getBookingsByUser(userPhone);

        if (!list.isEmpty()) {
            lvBookings.setVisibility(View.VISIBLE);
            tvNoBooking.setVisibility(View.GONE);
            lvBookings.setAdapter(new BookingCardAdapter(this, list));
        } else {
            lvBookings.setVisibility(View.GONE);
            tvNoBooking.setVisibility(View.VISIBLE);
        }
    }
}