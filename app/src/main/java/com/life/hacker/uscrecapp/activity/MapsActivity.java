package com.life.hacker.uscrecapp.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.databinding.ActivityMapsBinding;
import com.life.hacker.uscrecapp.model.Center;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
//    private ActivityMapsBinding binding;
    LatLng LyonCenter = new LatLng(34.02356070336721, -118.2887904971078);

    List<Center> centerList = new ArrayList<>();
    public void setCenters(List<Center> centerList) {
        this.centerList = centerList;
        for(int i = 0; i < centerList.size(); i++) {
            Center c = centerList.get(i);
            mMap.addMarker(new MarkerOptions().position(new LatLng(c.getLatitude(), c.getLongitude()))
                    .title(String.valueOf(c.getName())));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String markerTitle = marker.getTitle();
                Intent i = new Intent(MapsActivity.this, BookingActivity.class);
                startActivity(i);
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button SummaryButton = (Button) findViewById(R.id.goToSummaryButton);

        SummaryButton.setOnClickListener(view -> {
            startActivity(new Intent(MapsActivity.this, SummaryActivity.class));
        });

        MessageCenter.GetInstance().GetCenterlistRequest(MapsActivity.this);

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