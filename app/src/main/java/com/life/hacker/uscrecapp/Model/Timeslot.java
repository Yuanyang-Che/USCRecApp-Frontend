package com.life.hacker.uscrecapp.Model;

import java.util.Set;

public class Timeslot {
    public Timeslot(int timeIndex, int capacity, int size, Set<User> waitlist, Day day) {
        // timeIndex: 0: 8AM, 1:9AM ... every timeslot lasts for an hour
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

    private int timeIndex;
    private int capacity;
    private int size;
    private Set<User> waitlist;
    private Day day;




}
