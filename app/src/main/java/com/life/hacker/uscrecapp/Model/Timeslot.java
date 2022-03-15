package com.life.hacker.uscrecapp.Model;

import java.util.List;

public class Timeslot {
    public Timeslot(int timeIndex, int capacity, int size, List<User> waitlist, Day day) {
        this.timeIndex = timeIndex;
        this.capacity = capacity;
        this.size = size;
        this.waitlist = waitlist;
        this.day = day;
    }

    public int getTimeIndex() {
        return timeIndex;
    }

    public void setTimeIndex(int timeIndex) {
        this.timeIndex = timeIndex;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<User> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(List<User> waitlist) {
        this.waitlist = waitlist;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    private int timeIndex;
    private int capacity;
    private int size;
    private List<User> waitlist;
    Day day;


}
