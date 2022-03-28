package com.life.hacker.uscrecapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.life.hacker.uscrecapp.NotificationEntry;
import com.life.hacker.uscrecapp.NotificationQueue;
import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.adapter.NotificationAdapter;
import com.life.hacker.uscrecapp.adapter.TimeslotListAdapter;
import com.life.hacker.uscrecapp.model.Timeslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class NotificationCenterActivity extends AppCompatActivity {
    private Button backtoMapButton;
    private ListView mListView;
    private List<NotificationEntry> timeSlotList = new ArrayList<>();

    public void refreshPage() {
        runOnUiThread(this::recreate);
    }

    public void takeToastMessage(String message) {
        runOnUiThread(() -> Util.takeToastMessage(getApplicationContext(), message));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_center);

        backtoMapButton = findViewById(R.id.notificationBackToMapBtn);
        backtoMapButton.setOnClickListener(view ->
                startActivity(new Intent(NotificationCenterActivity.this, MapsActivity.class)));

        Queue<NotificationEntry> q = NotificationQueue.getInstance().getTimeslots();
        while(!q.isEmpty()) {
            timeSlotList.add(q.poll());
        }

        ListView mListView = findViewById(R.id.NotificationListView);
        NotificationAdapter adapter = new NotificationAdapter(this, R.layout.waitlist_adapter, timeSlotList);
        mListView.setAdapter(adapter);
    }
}