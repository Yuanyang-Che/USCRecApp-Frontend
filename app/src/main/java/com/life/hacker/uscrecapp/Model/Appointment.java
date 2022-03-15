package com.life.hacker.uscrecapp.Model;

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
