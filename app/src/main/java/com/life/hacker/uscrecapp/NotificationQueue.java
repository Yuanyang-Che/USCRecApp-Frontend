package com.life.hacker.uscrecapp;

import com.life.hacker.uscrecapp.model.Timeslot;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NotificationQueue {
    private static class InstanceHolder {
        private static final NotificationQueue instance = new NotificationQueue();
    }

    private NotificationQueue() {
        timeslots = new ConcurrentLinkedQueue();
    }

    public static NotificationQueue getInstance() {
        return InstanceHolder.instance;
    }

    public Queue<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void addTimeslot(Timeslot timeslot) {
        timeslots.add(timeslot);
    }

    private Queue<Timeslot> timeslots;

    private Set<Timeslot> timeslot_set;
}
