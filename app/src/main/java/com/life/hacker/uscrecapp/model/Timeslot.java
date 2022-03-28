package com.life.hacker.uscrecapp.model;

import java.util.Set;

public class Timeslot {
    public Timeslot(int timeIndex, int capacity, int size, Day day,
                    boolean isPast, boolean isBooked, boolean isWaitListed) {
        // timeIndex: 0 -> 0:00
        this.timeIndex = timeIndex;
        this.capacity = capacity;
        this.size = size;
        this.day = day;
        this.isPast = isPast;
        this.isBooked = isBooked;
        this.isWaitListed = isWaitListed;
    }

    public int getTimeIndex() {
        return timeIndex;
    }

    public Day getDay() {
        return day;
    }

    public boolean isBookable() {
        return size < capacity;
    }

    public boolean isPast() {
        return isPast;
    }

    public void setPast(boolean past) {
        isPast = past;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public boolean isWaitListed() {
        return isWaitListed;
    }

    public void setWaitListed(boolean waitListed) {
        isWaitListed = waitListed;
    }

    private int timeIndex;
    private int capacity;
    private int size;
    private Day day;
    private boolean isPast;
    private boolean isBooked;
    private boolean isWaitListed;




}
