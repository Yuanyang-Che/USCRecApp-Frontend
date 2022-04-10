package com.life.hacker.uscrecapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.life.hacker.uscrecapp.Notification.NotificationEntry;
import com.life.hacker.uscrecapp.Notification.NotificationListener;
import com.life.hacker.uscrecapp.Notification.NotificationQueue;
import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

public class NotificationCenterActivity extends AppCompatActivity implements NotificationListener {
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
    protected void onDestroy() {
        super.onDestroy();
        NotificationQueue.getInstance().removeListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_center);

        backtoMapButton = findViewById(R.id.notificationBackToMapBtn);
        backtoMapButton.setOnClickListener(view ->
                startActivity(new Intent(NotificationCenterActivity.this, MapsActivity.class)));

        Queue<NotificationEntry> q = NotificationQueue.getInstance().getAndClearTimeslots();
        updateList(q);
        NotificationQueue.getInstance().addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NotificationQueue.getInstance().removeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationQueue.getInstance().addListener(this);
    }

    @Override
    public void OnNotification() {
        Queue<NotificationEntry> q = NotificationQueue.getInstance().getAndClearTimeslots();
        runOnUiThread(() -> updateList(q));
    }

    private void updateList(Queue<NotificationEntry> q) {
        while(!q.isEmpty()) {
            timeSlotList.add(q.poll());
        }

        ListView mListView = findViewById(R.id.NotificationListView);
        NotificationAdapter adapter = new NotificationAdapter(this, R.layout.waitlist_adapter, timeSlotList);
        mListView.setAdapter(adapter);
    }

    public void removeTimeslot(int timeIndex, Date d, String centername) {
        NotificationEntry entry = new NotificationEntry(timeIndex, d, centername);
        timeSlotList.remove(entry);
    }

    public void recreateListView() {
        runOnUiThread(() -> {
            ListView mListView = findViewById(R.id.NotificationListView);
            NotificationAdapter adapter = new NotificationAdapter(this, R.layout.waitlist_adapter, timeSlotList);
            mListView.setAdapter(adapter);
        });
    }

}