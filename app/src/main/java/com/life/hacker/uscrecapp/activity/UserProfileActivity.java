package com.life.hacker.uscrecapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.SessionData;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView emailTextView = findViewById(R.id.userProfileTextViewEmail);
        TextView usernameTextView = findViewById(R.id.userProfileTextViewUserName);
        TextView netidTextView = findViewById(R.id.userProfileTextViewNetID);
        ImageView avatar = findViewById(R.id.userProfileImageViewAvatar);

        String email = SessionData.getInstance().getUser().getEmail();
        String username = SessionData.getInstance().getUser().getUsername();
        String netid = SessionData.getInstance().getUser().getNetid();
        Bitmap bitmap = SessionData.getInstance().getUser().getAvatar();

        emailTextView.setText(email);
        usernameTextView.setText(username);
        netidTextView.setText(netid);
        avatar.setImageBitmap(bitmap);

        Button backtoMapButton = findViewById(R.id.userProfileButtonBackToMap);
        backtoMapButton.setOnClickListener(view -> startActivity(new Intent(UserProfileActivity.this, MapsActivity.class)));
    }
}