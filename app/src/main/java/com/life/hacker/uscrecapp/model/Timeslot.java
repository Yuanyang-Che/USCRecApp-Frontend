package com.life.hacker.uscrecapp.model;

import java.util.Set;

public class Timeslot {
    public Timeslot(int timeIndex, int capacity, int size, Set<User> waitlist, Day day, boolean isPast) {
        // timeIndex: 0 -> 0:00
        this.timeIndex = timeIndex;
        this.capacity = capacity;
        this.size = size;
        this.waitlist = waitlist;
        this.day = day;
        this.isPast = isPast;
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

    public Set<User> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(Set<User> waitlist) {
        this.waitlist = waitlist;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void addUserToWaitlist(User user) {
        waitlist.add(user);
    }

    public void removeUserToWaitlist(User user) {
        waitlist.remove(user);
    }

    public boolean isBookable() {
        return size < capacity;
    }

    public void cancel() {
        size--;
    }

    public void book() {
        size++;
    }

    public boolean isPast() {
        return isPast;
    }

    public void setPast(boolean past) {
        isPast = past;
    }

    private int timeIndex;
    private int capacity;
    private int size;
    private Set<User> waitlist;
    private Day day;
    private boolean isPast;




}
