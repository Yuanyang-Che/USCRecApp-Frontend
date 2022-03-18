package com.life.hacker.uscrecapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.life.hacker.uscrecapp.Model.Day;
import com.life.hacker.uscrecapp.Model.Timeslot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        ListView mListView = (ListView) findViewById(R.id.bookingListView);

        Timeslot eight = new Timeslot(123, 10, 0, new HashSet<>(), new Day());
        Timeslot nine = new Timeslot(9, 10, 0, new HashSet<>(), new Day());
        Timeslot ten = new Timeslot(10, 10, 0, new HashSet<>(), new Day());
        Timeslot eleven = new Timeslot(11, 10, 0, new HashSet<>(), new Day());

        ArrayList<Timeslot> timeSlotList = new ArrayList<>();
        timeSlotList.add(eight);
        timeSlotList.add(nine);
        timeSlotList.add(ten);
        timeSlotList.add(eleven);

        TimeslotListAdapter adapter = new TimeslotListAdapter(this, R.layout.timeslot_adapter, timeSlotList);
        mListView.setAdapter(adapter);
    }
}