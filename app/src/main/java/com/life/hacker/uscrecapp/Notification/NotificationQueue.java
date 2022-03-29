package com.life.hacker.uscrecapp.Notification;

import java.util.LinkedList;
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
        ConcurrentHashMap<NotificationEntry, Integer> t = new ConcurrentHashMap<>();
        timeslot_set = t.newKeySet();

        ConcurrentHashMap<NotificationEntry, Integer> lis = new ConcurrentHashMap<>();
        listeners = lis.newKeySet();
    }

    public static NotificationQueue getInstance() {
        return InstanceHolder.instance;
    }

    public Queue<NotificationEntry> getAndClearTimeslots() {
        Queue<NotificationEntry> new_timeslots = new ConcurrentLinkedQueue<>(timeslots);
        timeslots.clear();
        timeslot_set.clear();

        return new_timeslots;
    }

    public Queue<NotificationEntry> getTimeslots() {
        return timeslots;
    }


    public void addListener(NotificationListener listener) {
        if(!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(NotificationListener listener) {
        if(listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void addTimeslot(NotificationEntry entry) {
        if (timeslot_set.contains(entry)) {
            return;
        }
        timeslots.add(entry);
        timeslot_set.add(entry);

        // notify

        for(NotificationListener listener : listeners) {
            listener.OnNotification();
        }
    }

    private Queue<NotificationEntry> timeslots;

    private Set<NotificationEntry> timeslot_set;

    private Set<NotificationListener> listeners;
}

