package com.life.hacker.uscrecapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.life.hacker.uscrecapp.R;

public class NotificationCenterActivity extends AppCompatActivity {
    private Button backtoMapButton;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_center);
    }
}