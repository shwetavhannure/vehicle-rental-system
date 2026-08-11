package com.example.bikerental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    GridView gvCities;
    ArrayList<CityModel> cityList;
    CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        gvCities = findViewById(R.id.gvCities);
        cityList = new ArrayList<>();


        cityList.add(new CityModel("Kolhapur", R.drawable.kolhapur));
        cityList.add(new CityModel("Sangli", R.drawable.sangali));
        cityList.add(new CityModel("Satara", R.drawable.satara));
        cityList.add(new CityModel("Solapur", R.drawable.solapur));
        cityList.add(new CityModel("Nashik", R.drawable.nashik));
        cityList.add(new CityModel("Nagpur", R.drawable.nagpur));

        cityAdapter = new CityAdapter(this, cityList);
        gvCities.setAdapter(cityAdapter);

        gvCities.setOnItemClickListener((parent, view, position, id) -> {
            CityModel clickedCity = cityList.get(position);
            String selectedCity = clickedCity.getName();

            SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);


            sp.edit().putString("lastCity", selectedCity)
                    .putBoolean("isSearching", false)
                    .apply();


            Intent intent = new Intent(DashboardActivity.this, VehicleActivity.class);
            intent.putExtra("CITY_NAME", selectedCity);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    static class CityModel {
        String name;
        int imageDrawableId;
        public CityModel(String name, int imageDrawableId) {
            this.name = name;
            this.imageDrawableId = imageDrawableId;
        }
        public String getName() { return name; }
        public int getImageDrawableId() { return imageDrawableId; }
    }

    class CityAdapter extends BaseAdapter {
        Context context;
        ArrayList<CityModel> cities;

        public CityAdapter(Context context, ArrayList<CityModel> cities) {
            this.context = context;
            this.cities = cities;
        }

        @Override
        public int getCount() { return cities.size(); }
        @Override
        public Object getItem(int position) { return cities.get(position); }
        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false);
            }
            ImageView ivCityImage = convertView.findViewById(R.id.ivCityImage);
            TextView tvCityName = convertView.findViewById(R.id.tvCityName);
            CityModel city = cities.get(position);
            tvCityName.setText(city.getName());
            ivCityImage.setImageResource(city.getImageDrawableId());
            return convertView;
        }
    }
}