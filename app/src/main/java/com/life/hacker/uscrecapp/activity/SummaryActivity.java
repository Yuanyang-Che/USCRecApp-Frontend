package com.life.hacker.uscrecapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.adapter.SummaryAdapter;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

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

        backtoMapButton.setOnClickListener(view -> startActivity(new Intent(SummaryActivity.this, MapsActivity.class)));

        MessageCenter.getInstance().HistoryRequest(SessionData.getInstance().getToken(), SummaryActivity.this);
    }

}