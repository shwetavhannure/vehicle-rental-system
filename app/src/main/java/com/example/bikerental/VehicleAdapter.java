package com.example.bikerental;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private ArrayList<VehicleModel> vehicleList;
    private final int totalDays;

    public VehicleAdapter(ArrayList<VehicleModel> vehicleList, int totalDays) {
        this.vehicleList = vehicleList;
        this.totalDays = totalDays <= 0 ? 1 : totalDays;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle_card, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        VehicleModel vehicle = vehicleList.get(position);

        Log.d("IMAGE_DEBUG", "Binding: " + vehicle.getName() + " with ResID: " + vehicle.getImageRes());

        holder.tvTitle.setText(vehicle.getName());
        holder.ivVehicle.setImageResource(vehicle.getImageRes());
        holder.tvDayRate.setText("Starting from ₹" + vehicle.getDayRate() + "/day");
        holder.tvKm.setText(vehicle.getKmLimit());
        holder.tvPrice.setText("₹" + (vehicle.getDayRate() * totalDays));

        if (vehicle.getIsAvailable() == 0) {
            holder.btnBook.setEnabled(false);
            holder.btnBook.setAlpha(0.5f);
            holder.btnBook.setText("Sold Out");
        } else {
            holder.btnBook.setEnabled(true);
            holder.btnBook.setAlpha(1.0f);
            holder.btnBook.setText("Book Now");
        }

        holder.btnBook.setOnClickListener(v -> {
            Context context = v.getContext();


            Toast.makeText(context, "Processing your booking...", Toast.LENGTH_SHORT).show();


            holder.btnBook.setEnabled(false);


            new android.os.Handler(context.getMainLooper()).postDelayed(() -> {
                DatabaseHelper db = new DatabaseHelper(context);




                SharedPreferences sp = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                String userPhone = sp.getString("etPhone", "");


                db.bookVehicle(vehicle.getId());
                vehicle.setIsAvailable(0);

                String start = sp.getString("fleet_start", "N/A");
                String end = sp.getString("fleet_end", "N/A");
                String city = sp.getString("fleet_city", "N/A");

                Date now = new Date();
                String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(now);
                String currentDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(now);


                boolean inserted = db.insertBooking(
                        userPhone,
                        vehicle.getName(),
                        start,
                        end,
                        currentTime,
                        currentDay,
                        city,
                        (vehicle.getDayRate() * totalDays),
                        vehicle.getType(),
                        vehicle.getImageRes()
                );

                if (inserted) {
                    holder.btnBook.setText("Sold Out");
                    holder.btnBook.setAlpha(0.5f);
                    Toast.makeText(context, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                } else {

                    holder.btnBook.setEnabled(true);
                    Toast.makeText(context, "Booking Failed!", Toast.LENGTH_SHORT).show();
                }
            }, 2000);
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public void updateList(ArrayList<VehicleModel> newList) {
        this.vehicleList = newList;
        notifyDataSetChanged();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvDayRate, tvKm;
        ImageView ivVehicle;
        Button btnBook;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvCardTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDayRate = itemView.findViewById(R.id.tvDayRate);
            tvKm = itemView.findViewById(R.id.tvKm);
            ivVehicle = itemView.findViewById(R.id.ivVehicle);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}