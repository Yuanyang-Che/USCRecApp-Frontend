package com.life.hacker.uscrecapp;

import com.life.hacker.uscrecapp.model.Timeslot;

import java.util.List;

public class NotificationQueue {
    private static class InstanceHolder {
        private static final NotificationQueue instance = new NotificationQueue();
    }

    public NotificationQueue getInstance() {
        return InstanceHolder.instance;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void addTimeslot(Timeslot timeslot) {
        timeslots.add(timeslot);
    }

    private List<Timeslot> timeslots;
}
