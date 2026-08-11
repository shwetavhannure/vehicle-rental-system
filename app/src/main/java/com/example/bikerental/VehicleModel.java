package com.example.bikerental;

public class VehicleModel {
    private int id;
    private int price;
    private int dayRate;
    private int imageRes;
    private int isAvailable;
    private String name;
    private String type;
    private String kmLimit;
    private String cityName; // Added this field
    private long bookingTime;

    /**
     * Updated Constructor
     * Added cityName to the end of the parameters
     */
    public VehicleModel(String name, int price, int dayRate, String kmLimit, String type, int imageRes, String cityName) {
        this.name = name;
        this.price = price;
        this.dayRate = dayRate;
        this.kmLimit = kmLimit;
        this.type = type;
        this.imageRes = imageRes;
        this.cityName = cityName;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getDayRate() { return dayRate; }
    public void setDayRate(int dayRate) { this.dayRate = dayRate; }

    public String getKmLimit() { return kmLimit; }
    public void setKmLimit(String kmLimit) { this.kmLimit = kmLimit; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getImageRes() { return imageRes; }
    public void setImageRes(int imageRes) { this.imageRes = imageRes; }

    public int getIsAvailable() { return isAvailable; }
    public void setIsAvailable(int isAvailable) { this.isAvailable = isAvailable; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public long getBookingTime() { return bookingTime; }
    public void setBookingTime(long bookingTime) { this.bookingTime = bookingTime; }
}