package com.life.hacker.uscrecapp.Model;

import java.util.Date;

public class Day {

    public Day(Date date, Center center, Timeslot[] timeslots) {
        this.date = date;
        this.center = center;
        this.timeslots = timeslots;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public Timeslot[] getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(Timeslot[] timeslots) {
        this.timeslots = timeslots;
    }

    private Date date;
    private Center center;
    private Timeslot[] timeslots;


}
