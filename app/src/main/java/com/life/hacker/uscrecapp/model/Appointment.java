package com.life.hacker.uscrecapp.model;

import java.util.List;
import java.util.Vector;

public class Appointment {
    private int id;
    private Timeslot timeslot;

    public Appointment(int id, Timeslot t) {
        this.id = id;
        this.timeslot = t;
    }

    public int getId() {
        return id;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }
}
