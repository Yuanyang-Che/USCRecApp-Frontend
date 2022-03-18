package com.life.hacker.uscrecapp.model;

public class Center {
    public Center(int id, String name, Day[] days, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.days = days;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Day[] getDays() {
        return days;
    }

    public void setDays(Day[] days) {
        this.days = days;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private int id;
    private String name;
    private Day[] days;
    private double latitude;
    private double longitude;
}
