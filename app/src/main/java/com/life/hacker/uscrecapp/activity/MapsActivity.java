package com.life.hacker.uscrecapp.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
//    private ActivityMapsBinding binding;
    ArrayList<LatLng> centers = new ArrayList<>();
    LatLng LyonCenter = new LatLng(34.02356070336721, -118.2887904971078);
    LatLng USCVillageFitnessCenter = new LatLng(34.02479913176758, -118.28598143534718);
    LatLng UyTengsuAquaticsCenter = new LatLng(34.02392489906327, -118.28842653793001);
    ArrayList<String> centerNames = new ArrayList<>();

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

        centers.add(LyonCenter);
        centers.add(USCVillageFitnessCenter);
        centers.add(UyTengsuAquaticsCenter);

        centerNames.add("Lyon Center");
        centerNames.add("USC Village Fitness Center");
        centerNames.add("UyTengsu Aquatics Center");
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

        for(int i = 0; i < centers.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(centers.get(i)).title(String.valueOf(centerNames.get(i))));
        }

        // Add a marker in Sydney and move the camera
//        LatLng LyonCenter = new LatLng(34.02356070336721, -118.2887904971078);
//        mMap.addMarker(new MarkerOptions().position(LyonCenter).title("Marker in LyonCenter"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LyonCenter, 15f));

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
}