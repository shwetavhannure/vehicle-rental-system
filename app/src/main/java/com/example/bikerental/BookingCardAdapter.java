package com.example.bikerental;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookingCardAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<BookingModel> bookingList;


    public BookingCardAdapter(Context context, ArrayList<BookingModel> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @Override public int getCount() { return bookingList.size(); }
    @Override public Object getItem(int position) { return bookingList.get(position); }
    @Override public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_booking_history_card, parent, false);
        }


        BookingModel b = bookingList.get(position);


        TextView tvName = convertView.findViewById(R.id.tvBookingVehicleName);
        TextView tvID = convertView.findViewById(R.id.tvBookingIDValue);
        TextView tvFare = convertView.findViewById(R.id.tvTotalFare);
        TextView tvStartDateTime = convertView.findViewById(R.id.tvBookingStartDateTime);
        TextView tvEndDateTime = convertView.findViewById(R.id.tvBookingEndDateTime);
        TextView tvStartCity = convertView.findViewById(R.id.tvStartCity);
        TextView tvEndCity = convertView.findViewById(R.id.tvEndCity);
        ImageView ivVehicle = convertView.findViewById(R.id.ivBookingVehicle);
        LinearLayout llViewLocation = convertView.findViewById(R.id.llViewLocation);
        LinearLayout llCancelBooking = convertView.findViewById(R.id.llCancelBooking);


        tvName.setText(b.name);
        tvID.setText("Booking ID: BR00" + b.id);
        tvFare.setText("₹" + b.price);

        String shortDay = (b.dayName != null && b.dayName.length() >= 3) ? b.dayName.substring(0, 3) : "Day";
        tvStartDateTime.setText(shortDay + ", " + b.start + " • " + b.time);
        tvEndDateTime.setText(shortDay + ", " + b.end + " • " + b.time);
        tvStartCity.setText(b.cityName);
        tvEndCity.setText(b.cityName);

        if (b.imageRes != 0) {
            ivVehicle.setImageResource(b.imageRes);
        } else {
            ivVehicle.setImageResource(b.type != null && b.type.equals("scooter") ? R.drawable.scooter1 : R.drawable.bike2);
        }


        llViewLocation.setOnClickListener(v -> {
            String lat = "", lng = "", label = "";
            switch (b.cityName) {
                case "Kolhapur": lat = "16.7097"; lng = "74.2433"; label = "Tarabai Park, Kolhapur"; break;
                case "Satara":   lat = "17.6805"; lng = "73.9940"; label = "Powai Naka, Satara"; break;
                case "Sangli":   lat = "16.8524"; lng = "74.5815"; label = "Madhavnagar Road, Sangli"; break;
                case "Nashik":   lat = "19.9975"; lng = "73.7898"; label = "Gangapur Road, Nashik"; break;
                case "Nagpur":   lat = "21.1458"; lng = "79.0882"; label = "Sadar, Nagpur"; break;
                case "Solapur":  lat = "17.6599"; lng = "75.9064"; label = "Hotgi Road, Solapur"; break;
                default:         lat = "0"; lng = "0"; label = b.cityName; break;
            }

            Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lng + "?q=" + lat + "," + lng + "(" + Uri.encode(label) + ")");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            } else {
                Toast.makeText(context, "Google Maps not installed", Toast.LENGTH_SHORT).show();
            }
        });


        llCancelBooking.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(context);


            db.deleteBooking(b.id);
            db.makeVehicleAvailable(b.name);


            AppConfig.showCancellationMessage = true;


            Toast.makeText(context, "Booking Cancelled! Redirecting...", Toast.LENGTH_SHORT).show();


            new android.os.Handler(context.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(context, FleetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }, 2000);
        });

        return convertView;
    }
}