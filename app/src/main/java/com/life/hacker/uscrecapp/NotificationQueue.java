package com.life.hacker.uscrecapp;

public class NotificationQueue {
    private static class InstanceHolder {
        private static final NotificationQueue instance = new NotificationQueue();
    }

    public NotificationQueue getInstance() {
        return InstanceHolder.instance;
    }

}
