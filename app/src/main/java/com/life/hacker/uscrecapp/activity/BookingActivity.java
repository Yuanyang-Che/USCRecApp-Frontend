package com.life.hacker.uscrecapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.adapter.TimeslotListAdapter;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingActivity extends FragmentActivity {
    private Button backtoMapButton;
    private String centerName;
    private ArrayList<Timeslot> timeSlotList;

    public String getCenterName() {
        return centerName;
    }

    public void jumpBackToMap(String message) {
        Intent i = new Intent(BookingActivity.this, MapsActivity.class);
        i.putExtra("Message", message);
        startActivity(i);
    }

    public void setTimeSlotList(List<Timeslot> timeSlotList) {
        this.timeSlotList = new ArrayList<>(timeSlotList);

        ListView mListView = (ListView) findViewById(R.id.bookingListView);
        TimeslotListAdapter adapter = new TimeslotListAdapter(this, R.layout.timeslot_adapter, this.timeSlotList);
        mListView.setAdapter(adapter);

    }

    public void appendTimeSlotList(List<Timeslot> timeSlotList) {
        if (this.timeSlotList == null) {
            this.timeSlotList = new ArrayList<>(timeSlotList);
        } else {
            this.timeSlotList.addAll(timeSlotList);
        }

        ListView mListView = (ListView) findViewById(R.id.bookingListView);
        TimeslotListAdapter adapter = new TimeslotListAdapter(this, R.layout.timeslot_adapter, this.timeSlotList);
        mListView.setAdapter(adapter);
    }

    public void backendSuccessOrFail() {

    }

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    private Date findNextDay(Date date) {
        return new Date(date.getTime() + MILLIS_IN_A_DAY);
    }

    private Date findPrevDay(Date date) {
        return new Date(date.getTime() - MILLIS_IN_A_DAY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            centerName = (String) b.get("CenterName");
            //mapsActivity = (MapsActivity) b.get("MapInstance");
        }

        if (centerName != null) {
            String pattern = "yyyy-MM-dd";
            DateFormat df = new SimpleDateFormat(pattern);
            Date today = Calendar.getInstance().getTime();

            String todayAsString = df.format(today);
            MessageCenter.getInstance().GetTimeslotOfCenterOnDateRequest(centerName, todayAsString, BookingActivity.this);
        }

        backtoMapButton = (Button) findViewById(R.id.backtoMapButton);

        backtoMapButton.setOnClickListener(view -> {
            startActivity(new Intent(BookingActivity.this, MapsActivity.class));
        });
    }
}