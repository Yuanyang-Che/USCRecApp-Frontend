package com.life.hacker.uscrecapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.adapter.SummaryAdapter;
import com.life.hacker.uscrecapp.adapter.TimeslotListAdapter;
import com.life.hacker.uscrecapp.model.Day;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {
    private Button backtoMapButton;
    private ListView mListView;
    private List<Timeslot> timeSlotList;

    public void update(List<Timeslot> timeSlotList) {
        this.timeSlotList = timeSlotList;

        mListView = (ListView) findViewById(R.id.SummaryListView);
        try {

            SummaryAdapter adapter = new SummaryAdapter(this, R.layout.timeslot_adapter, timeSlotList);
            mListView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        backtoMapButton = (Button) findViewById(R.id.backtoMapButton2);


        //timeSlotList = new ArrayList<>();

//        Timeslot eight = new Timeslot(123, 123, 0, new HashSet<>(), new Day(), true);
//        Timeslot nine = new Timeslot(9, 9, 0, new HashSet<>(), new Day(), true);
//        Timeslot ten = new Timeslot(10, 10, 10, new HashSet<>(), new Day(), false);
//        Timeslot eleven = new Timeslot(11, 11, 11, new HashSet<>(), new Day(), false);


//        timeSlotList.add(eight);
//        timeSlotList.add(nine);
//        timeSlotList.add(ten);
//        timeSlotList.add(eleven);


        backtoMapButton.setOnClickListener(view -> {
            startActivity(new Intent(SummaryActivity.this, MapsActivity.class));
        });

        MessageCenter.GetInstance().HistoryRequest(SessionData.getInstance().getToken(), SummaryActivity.this);
    }

}