package com.example.alimama;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alimama.Model.MoodEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;

public class MoodMap extends AppCompatActivity implements MapViewFeedback, OnMapReadyCallback{

    private GoogleMap mMap;
    private Context context;

    private String currParticipant;
    private Database db = new Database();

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

    }


    @Override
    public void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation) {
        mMap.clear();
        IconGenerator iconGenerator = new IconGenerator(this);
        for(MoodEvent each: moodEventsWithLocation) {
            if (each.getEmoticon() != null) {
                GeoPoint location = each.getLocationOfMoodEvent();
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(),location.getLongitude()))
                        .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(each.getEmoticon())))); // change
            }

        }

    }

    @Override
    public void failRetrieveAllLocatedMoodEventsOfAParticipant(String message) {

    }

    @Override
    public void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
        mMap.clear();
        IconGenerator iconGenerator = new IconGenerator(this);
        for(MoodEvent each: mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
            if (each.getEmoticon() != null) {
                GeoPoint location = each.getLocationOfMoodEvent();
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(),location.getLongitude()))
                        .title(each.getUsername())
                        .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(each.getEmoticon()))));
                marker.showInfoWindow();
            }

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