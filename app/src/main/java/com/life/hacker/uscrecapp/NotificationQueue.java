package com.life.hacker.uscrecapp;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NotificationQueue {
    private static class InstanceHolder {
        private static final NotificationQueue instance = new NotificationQueue();
    }

    private NotificationQueue() {
        timeslots = new ConcurrentLinkedQueue();
        ConcurrentHashMap<NotificationEntry, Integer> map = new ConcurrentHashMap<>();
        timeslot_set = map.newKeySet();
    }

    public static NotificationQueue getInstance() {
        return InstanceHolder.instance;
    }

    public Queue<NotificationEntry> getTimeslots() {
        return timeslots;
    }

    public void addTimeslot(NotificationEntry entry) {
        if (timeslot_set.contains(entry)) {
            System.out.println("hi");
            return;
        }
        timeslots.add(entry);
        timeslot_set.add(entry);
    }

    private Queue<NotificationEntry> timeslots;

    private Set<NotificationEntry> timeslot_set;
}

