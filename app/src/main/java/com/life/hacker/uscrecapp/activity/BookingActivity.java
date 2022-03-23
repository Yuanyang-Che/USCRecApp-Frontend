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
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class BookingActivity extends Activity {
    private Button backtoMapButton;
    String centerName;
    ArrayList<Timeslot> timeSlotList;

    public void setTimeSlotList(List<Timeslot> timeSlotList){
        this.timeSlotList = new ArrayList<>(timeSlotList);

        ListView mListView = (ListView) findViewById(R.id.bookingListView);
        TimeslotListAdapter adapter = new TimeslotListAdapter(this, R.layout.timeslot_adapter, this.timeSlotList);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Bundle b = getIntent().getExtras();
        if(b != null) {
            centerName = (String) b.get("CenterName");
        }

        if(centerName != null) {
            String pattern = "yyyy-MM-dd";
            DateFormat df = new SimpleDateFormat(pattern);
            Date today = Calendar.getInstance().getTime();
            String todayAsString = df.format(today);
            MessageCenter.GetInstance().GetTimeslotOfCenterOnDateRequest(centerName, todayAsString, BookingActivity.this);
        }

        backtoMapButton = (Button) findViewById(R.id.backtoMapButton);
//        Timeslot eight = new Timeslot(123, 123, 0, new HashSet<>(), new Day(), false);
//        Timeslot nine = new Timeslot(9, 9, 0, new HashSet<>(), new Day(), false);
//        Timeslot ten = new Timeslot(10, 10, 10, new HashSet<>(), new Day(), false);
//        Timeslot eleven = new Timeslot(11, 11, 11, new HashSet<>(), new Day(), false);
//
//        ArrayList<Timeslot> timeSlotList = new ArrayList<>();
//        timeSlotList.add(eight);
//        timeSlotList.add(nine);
//        timeSlotList.add(ten);
//        timeSlotList.add(eleven);

        backtoMapButton.setOnClickListener(view -> {
            startActivity(new Intent(BookingActivity.this, MapsActivity.class));
        });
    }
}