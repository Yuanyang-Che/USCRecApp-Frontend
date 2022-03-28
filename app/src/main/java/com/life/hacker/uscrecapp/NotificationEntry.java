package com.life.hacker.uscrecapp;

import java.util.Date;

public class NotificationEntry {
    private int timeIndex;
    private Date d;
    private String centerName;

    public NotificationEntry(int timeIndex, Date d, String centerName) {
        this.d = d;
        this.centerName = centerName;
        this.timeIndex = timeIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NotificationEntry that = (NotificationEntry) o;
        return timeIndex == that.timeIndex && d.equals(that.d) && centerName.equals(centerName);
    }

    @Override
    public int hashCode() {
        return timeIndex + d.hashCode() + centerName.hashCode();
    }


}
