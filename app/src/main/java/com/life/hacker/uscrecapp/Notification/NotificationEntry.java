package com.life.hacker.uscrecapp.Notification;

import java.util.Date;

public class NotificationEntry {
    public int getTimeIndex() {
        return timeIndex;
    }

    public Date getD() {
        return d;
    }

    public String getCenterName() {
        return centerName;
    }

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
