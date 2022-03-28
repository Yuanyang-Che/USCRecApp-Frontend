package com.life.hacker.uscrecapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.adapter.TimeslotListAdapter;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingActivity extends FragmentActivity {

    private Button backtoMapButton;
    private Button prevDayButton, nextDayButton;
    private TextView dateText;

    private Date currentDate;
    private String centerName;
    //private ArrayList<Timeslot> timeSlotList;

    public String getCenterName() {
        return centerName;
    }

    public void jumpBackToMap(String message) {
        Intent i = new Intent(BookingActivity.this, MapsActivity.class);
        i.putExtra("Message", message);
        startActivity(i);
    }

    public void setTimeSlotList(List<Timeslot> timeSlotList) {
        //this.timeSlotList = new ArrayList<>(timeSlotList);

        ListView mListView = findViewById(R.id.bookingListView);
        TimeslotListAdapter adapter = new TimeslotListAdapter(this, R.layout.timeslot_adapter, timeSlotList);
        mListView.setAdapter(adapter);
    }

    /*public void appendTimeSlotList(List<Timeslot> timeSlotList) {
        if (this.timeSlotList == null) {
            this.timeSlotList = new ArrayList<>(timeSlotList);
        } else {
            this.timeSlotList.addAll(timeSlotList);
        }

        ListView mListView = (ListView) findViewById(R.id.bookingListView);
        TimeslotListAdapter adapter = new TimeslotListAdapter(this, R.layout.timeslot_adapter, this.timeSlotList);
        mListView.setAdapter(adapter);
    }*/

    private void refreshPage() {
        dateText.setText(Util.formatDateToStardard(currentDate));
        String currDateStr = Util.formatDateToStardard(currentDate);
        MessageCenter.getInstance().GetTimeslotOfCenterOnDateRequest(centerName, currDateStr, BookingActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPage();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            centerName = (String) b.get("CenterName");
        }

        currentDate = Calendar.getInstance().getTime();
        //TODO findviewbyid to find the 2 btns and 1 textview
        backtoMapButton = findViewById(R.id.backtoMapButton);

        prevDayButton = findViewById(R.id.prevDay);
        nextDayButton = findViewById(R.id.nextDay);
        dateText = findViewById(R.id.selectedDate);

        prevDayButton.setOnClickListener(view -> {
            currentDate = Util.findPrevDay(currentDate);
            refreshPage();
        });

        nextDayButton.setOnClickListener(view -> {
            currentDate = Util.findNextDay(currentDate);
            refreshPage();
        });

        backtoMapButton.setOnClickListener(view -> {
            startActivity(new Intent(BookingActivity.this, MapsActivity.class));
        });

        if (centerName != null) {
            refreshPage();
        }
    }
}