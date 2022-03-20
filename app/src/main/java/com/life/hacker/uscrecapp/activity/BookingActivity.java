package com.life.hacker.uscrecapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.adapter.TimeslotListAdapter;
import com.life.hacker.uscrecapp.model.Day;
import com.life.hacker.uscrecapp.model.Timeslot;

import java.util.ArrayList;
import java.util.HashSet;

public class BookingActivity extends Activity {
    private Button backtoMapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        backtoMapButton = (Button) findViewById(R.id.backtoMapButton);

        ListView mListView = (ListView) findViewById(R.id.bookingListView);

        Timeslot eight = new Timeslot(123, 123, 0, new HashSet<>(), new Day());
        Timeslot nine = new Timeslot(9, 9, 0, new HashSet<>(), new Day());
        Timeslot ten = new Timeslot(10, 10, 10, new HashSet<>(), new Day());
        Timeslot eleven = new Timeslot(11, 11, 11, new HashSet<>(), new Day());

        ArrayList<Timeslot> timeSlotList = new ArrayList<>();
        timeSlotList.add(eight);
        timeSlotList.add(nine);
        timeSlotList.add(ten);
        timeSlotList.add(eleven);

        TimeslotListAdapter adapter = new TimeslotListAdapter(this, R.layout.timeslot_adapter, timeSlotList);
        mListView.setAdapter(adapter);

        backtoMapButton.setOnClickListener(view -> {
            startActivity(new Intent(BookingActivity.this, MapsActivity.class));
        });
    }
}