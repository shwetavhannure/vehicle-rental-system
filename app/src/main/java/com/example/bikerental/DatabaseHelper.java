package com.example.bikerental;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "BikeApp.db";

    public static final int DB_VERSION = 18;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT UNIQUE, email TEXT UNIQUE)");


        db.execSQL("CREATE TABLE IF NOT EXISTS vehicles(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER, dayRate INTEGER, kmLimit TEXT, type TEXT, imageRes INTEGER, isAvailable INTEGER DEFAULT 1, bookingTime INTEGER DEFAULT 0, cityName TEXT)");


        db.execSQL("CREATE TABLE IF NOT EXISTS bookings(id INTEGER PRIMARY KEY AUTOINCREMENT, userPhone TEXT, vehicleName TEXT, startDate TEXT, endDate TEXT, time TEXT, dayName TEXT, city TEXT, price INTEGER, vehicleType TEXT, imageRes INTEGER)");

        insertInitialVehicles(db);
    }

    private void insertInitialVehicles(SQLiteDatabase db) {

        insertVehicle(db, "HERO | HF Deluxe", 1199, 350, "120 KM", "bike", R.drawable.hero_hfdeluxe_kolhapur, "Kolhapur");
        insertVehicle(db, "Royal Enfield", 2500, 649, "150 KM", "bike", R.drawable.royalenfield_kolhapur2, "Kolhapur");
        insertVehicle(db, "Hero | Xoom", 649, 449, "100 KM", "scooter", R.drawable.hero_xoom_kolhapur, "Kolhapur");
        insertVehicle(db, "Honda | Activa", 750, 220, "110 KM", "scooter", R.drawable.honda__activa_kolhapur, "Kolhapur");
        insertVehicle(db, "TVS | Jupiter", 720, 210, "105 KM", "scooter", R.drawable.tvs_jupiter_kolhapur, "Kolhapur");


        insertVehicle(db, "Hero | Pleasure Plus", 700, 200, "100 KM", "scooter", R.drawable.hero_pleasureplus_sangali, "Sangli");
        insertVehicle(db, "Harley Davidson", 2500, 800, "200 KM", "bike", R.drawable.harleydavidson_sangli2, "Sangli");
        insertVehicle(db, "Hero | Splendour Plus", 850, 250, "130 KM", "bike", R.drawable.hero_splendourplus_sangli2, "Sangli");
        insertVehicle(db, "Hero | Glamour XTec", 950, 300, "140 KM", "bike", R.drawable.hero_glamourxtec_sangli2, "Sangli");
        insertVehicle(db, "VIDA | V2 Plus", 1100, 350, "110 KM", "scooter", R.drawable.vida_v2plus_sangli2, "Sangli");


        insertVehicle(db, "Hero | Xtreme 160R", 1200, 400, "150 KM", "bike", R.drawable.hero_xtreme_160r_satara, "Satara");
        insertVehicle(db, "Hero | Glamour", 950, 300, "140 KM", "bike", R.drawable.hero_glamour_satara2, "Satara");
        insertVehicle(db, "Hero | Xoom 125", 800, 250, "120 KM", "scooter", R.drawable.hero_xoom125_satara2, "Satara");
        insertVehicle(db, "Hero | Destini 125", 750, 220, "110 KM", "scooter", R.drawable.hero_destini125_satara2, "Satara");


        insertVehicle(db, "Ampere | Electric Scooter", 600, 150, "90 KM", "scooter", R.drawable.ampere_electricscooter_solapur2, "Solapur");
        insertVehicle(db, "Hero | XPulse 200", 1500, 500, "180 KM", "bike", R.drawable.hero_xpulse200_solapur, "Solapur");
        insertVehicle(db, "Hero | Maestro Edge 125", 700, 200, "110 KM", "scooter", R.drawable.hero_maestroedge125_solapur2, "Solapur");
        insertVehicle(db, "Royal Enfield | Classic 350", 1500, 900, "110 KM", "bike", R.drawable.royalenfield_classic350_solapur2, "Solapur");


        insertVehicle(db, "Hero | Splendour Plus", 850, 250, "130 KM", "bike", R.drawable.hero_splendourplus_nashik2, "Nashik");
        insertVehicle(db, "Hero | Passion XPro", 900, 280, "135 KM", "bike", R.drawable.hero_passionxpro_nashik, "Nashik");
        insertVehicle(db, "Royal Enfield | Hunter 350", 2200, 750, "160 KM", "bike", R.drawable.royalenfield_hunter350_nashik2, "Nashik");
        insertVehicle(db, "Suzuki | Access", 750, 220, "110 KM", "scooter", R.drawable.suzuki_access_nashik2, "Nashik");
        insertVehicle(db, "Honda | Activa 125", 800, 230, "115 KM", "scooter", R.drawable.honda_activa125_nashik2, "Nashik");


        insertVehicle(db, "Hero | Xoom", 650, 200, "100 KM", "scooter", R.drawable.hero_xoom_nagpur, "Nagpur");
        insertVehicle(db, "Hero | Xtreme 200 S", 1300, 450, "160 KM", "bike", R.drawable.hero_xtreme_200s_nagpur2, "Nagpur");
        insertVehicle(db, "Hero | Pleasure Plus", 700, 200, "100 KM", "scooter", R.drawable.hero_pleasureolus_nagpur, "Nagpur");
        insertVehicle(db, "Hero | Xpulse 200 4V", 1500, 500, "180 KM", "bike", R.drawable.hero_xpulse200_4v_nagpur, "Nagpur");
        insertVehicle(db, "TVS | Ntorq 125", 900, 280, "120 KM", "scooter", R.drawable.tvs_ntorq125_nagpur3, "Nagpur");
        insertVehicle(db, "Vida | V1 Pro", 1200, 400, "150 KM", "scooter", R.drawable.vida_v1pro_nagpur2, "Nagpur");
    }

    private void insertVehicle(SQLiteDatabase db, String name, int price, int dayRate, String kmLimit, String type, int imageRes, String cityName) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("price", price);
        cv.put("dayRate", dayRate);
        cv.put("kmLimit", kmLimit);
        cv.put("type", type);
        cv.put("imageRes", imageRes);
        cv.put("cityName", cityName);
        db.insert("vehicles", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS vehicles");
        db.execSQL("DROP TABLE IF EXISTS bookings");
        onCreate(db);
    }


    public ArrayList<VehicleModel> getVehiclesByCity(String city) {
        ArrayList<VehicleModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM vehicles WHERE cityName=?", new String[]{city});

        if (cursor.moveToFirst()) {
            do {
                VehicleModel v = new VehicleModel(cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(9));
                v.setId(cursor.getInt(0));
                v.setIsAvailable(cursor.getInt(7));
                v.setBookingTime(cursor.getLong(8));
                list.add(v);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean checkUser(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE phone=?", new String[]{phone});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String getUserName(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM users WHERE phone=?", new String[]{phone});
        String name = null;
        if (cursor.moveToFirst()) name = cursor.getString(0);
        cursor.close();
        return name;
    }

    public boolean insertUser(String name, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("email", email);
        return db.insert("users", null, cv) != -1;
    }

    public void bookVehicle(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isAvailable", 0);
        cv.put("bookingTime", System.currentTimeMillis());
        db.update("vehicles", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public boolean insertBooking(String userPhone, String vehicleName, String startDate, String endDate, String time,
                                 String dayName, String city, int price, String vehicleType, int imageRes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userPhone", userPhone);
        cv.put("vehicleName", vehicleName);
        cv.put("startDate", startDate);
        cv.put("endDate", endDate);
        cv.put("time", time);
        cv.put("dayName", dayName);
        cv.put("city", city);
        cv.put("price", price);
        cv.put("vehicleType", vehicleType);
        cv.put("imageRes", imageRes);
        return db.insert("bookings", null, cv) != -1;
    }


    public void deleteBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("bookings", "id=?", new String[]{String.valueOf(bookingId)});
    }


    public void makeVehicleAvailable(String vehicleName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isAvailable", 1);
        cv.put("bookingTime", 0);
        db.update("vehicles", cv, "name=?", new String[]{vehicleName});
    }


    public String getUserEmail(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email FROM users WHERE phone=?", new String[]{phone});
        String email = "Not provided";

        if (cursor.moveToFirst()) {
            email = cursor.getString(0);
        }
        cursor.close();
        return email;
    }

    public ArrayList<BookingModel> getBookingsByUser(String userPhone) {
        ArrayList<BookingModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM bookings WHERE userPhone = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userPhone});

        if (cursor.moveToFirst()) {
            do {

                BookingModel b = new BookingModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("vehicleName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("startDate")),
                        cursor.getString(cursor.getColumnIndexOrThrow("endDate")),
                        cursor.getString(cursor.getColumnIndexOrThrow("time")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dayName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("city")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("vehicleType")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("imageRes"))
                );
                list.add(b);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}