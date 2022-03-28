package com.life.hacker.uscrecapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.model.Center;
import com.life.hacker.uscrecapp.model.MapData;
import com.life.hacker.uscrecapp.model.User;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int notificationCount = 0;


    LatLng LyonCenter = new LatLng(34.02356070336721, -118.2887904971078);

    public void setCenters(List<Center> centerList) {
        MapData.getInstance().setCenters(centerList);

        for (Center c : centerList) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(c.getLatitude(), c.getLongitude()))
                    .title(String.valueOf(c.getName())));
            mMap.setOnMarkerClickListener(marker -> {
                String markerTitle = marker.getTitle();
                Intent i = new Intent(MapsActivity.this, BookingActivity.class);
                i.putExtra("CenterName", markerTitle);
                startActivity(i);
                return false;
            });
        }
    }

    public void takeMessage(String message) {
        Context context = getApplicationContext();
        Util.takeToastMessage(context, message);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String message = (String) b.get("Message");
            takeMessage(message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        Button summaryButton = findViewById(R.id.goToSummaryButton);
        summaryButton.setOnClickListener(view -> startActivity(new Intent(MapsActivity.this, SummaryActivity.class)));

        Button notificationButton = findViewById(R.id.goToNotificationButton);
        notificationButton.setText("notification center (" + notificationCount + ")");
        notificationButton.setOnClickListener(view -> startActivity(new Intent(MapsActivity.this, NotificationCenterActivity.class)));

        try{
            ImageView avatar = findViewById(R.id.Avatar);
            User u = SessionData.getInstance().getUser();
            avatar.setImageBitmap(SessionData.getInstance().getUser().getAvatar());
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        Button logoutBtn = findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(view -> {
            MessageCenter.getInstance().LogoutRequest(SessionData.getInstance().getToken(), MapsActivity.this);
        });
        MessageCenter.getInstance().GetCenterlistRequest(MapsActivity.this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
//        LatLng LyonCenter = new LatLng(34.02356070336721, -118.2887904971078);
//        mMap.addMarker(new MarkerOptions().position(LyonCenter).title("Marker in LyonCenter"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LyonCenter, 15f));


    }
}