package com.example.bikerental;

public class BookingModel {

    public int id, price, imageRes;
    public String name, start, end, time, dayName, cityName, type;

    /**
     * Updated Constructor
     * @param id        Unique Booking ID
     * @param name      Vehicle Name
     * @param start     Start Date
     * @param end       End Date
     * @param time      Time
     * @param dayName   Day of the week
     * @param cityName  City name
     * @param price     Total fare
     * @param type      Vehicle type
     * @param imageRes  The resource ID of the bike image
     */
    public BookingModel(int id, String name, String start, String end, String time,
                        String dayName, String cityName, int price, String type, int imageRes) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.time = time;
        this.dayName = dayName;
        this.cityName = cityName;
        this.price = price;
        this.type = type;
        this.imageRes = imageRes;
    }
}