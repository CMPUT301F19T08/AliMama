package com.example.alimama;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class MoodMap extends AppCompatActivity implements MapViewFeedback, OnMapReadyCallback{

    private GoogleMap mMap;
    private Context context;

    private String currParticipant;
    private Database db = new Database();
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_mood_map);
        final Button myMoodBtn = findViewById(R.id.mood_map_my_map_btn);
        final Button friendMoodBtn = findViewById(R.id.mood_map_friend_map_btn);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mood_map_map_fragment);
        currParticipant = getIntent().getStringExtra("USERNAME");
        mapFragment.getMapAsync(this);

        friendMoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(currParticipant, (MoodMap)context);
                friendMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                friendMoodBtn.setTextColor(Color.parseColor("#008577"));
                myMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008577")));
                myMoodBtn.setTextColor(Color.parseColor("#ffffff"));
            }
        });
        myMoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.retrieveAllLocatedMoodEventsOfAParticipant(currParticipant,(MoodMap)context);
                myMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                myMoodBtn.setTextColor(Color.parseColor("#008577"));
                friendMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008577")));
                friendMoodBtn.setTextColor(Color.parseColor("#ffffff"));

            }
        });
        // LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
     /*   if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } */
    }


    @Override
    public void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation) {
        mMap.clear();
        for(MoodEvent each: moodEventsWithLocation) {
            GeoPoint location = each.getLocationOfMoodEvent();
            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));

        }


    }

    @Override
    public void failRetrieveAllLocatedMoodEventsOfAParticipant(String message) {

    }

    @Override
    public void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
        mMap.clear();
        for(MoodEvent each: mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
            GeoPoint location = each.getLocationOfMoodEvent();
            String username = each.getUsername();
            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title(username));

        }




    }

    @Override
    public void failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String message) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setIndoorEnabled(true); // Indoor Map
        googleMap.setMyLocationEnabled(false); // Location Sharing
        UiSettings Ui = googleMap.getUiSettings();
        Ui.setZoomControlsEnabled(true); //Zoom in zoom out event
        Ui.setMapToolbarEnabled(false);
        Ui.setMyLocationButtonEnabled(false);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap = googleMap;

        LatLng UofA = new LatLng(53.526777, -113.527153); // Latitude, Longitude
//        mMap.addMarker(new MarkerOptions().position(UofA).title("Happy"));
        CameraPosition UAlberta = CameraPosition.builder()
                .target(UofA)
                .zoom(5)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(UAlberta), 3000, null);
//        mMap.addMarker(new MarkerOptions().position(new LatLng(53.528570, -113.524359)).title("Sad"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(53.527647, -113.524210)).title("Angry"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(53.526709, -113.521721)).title("Excited"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(53.526658, -113.524714)).title("Happy"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(53.526051, -113.526211)).title("Disappointment"));



    }
}