/**
 * MoodMap
 * Version 1.0
 * 06 November 2019
 * Copyright: UAlberta CMPUT301Fall2019 Group 8
 */
package com.example.alimama;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.example.alimama.Model.MoodEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.ui.IconGenerator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This is the Map View screen which allows users to view their mood history or their friends' mood
 * history on Map
 */

public class MoodMap extends AppCompatActivity implements MapViewFeedback, OnMapReadyCallback{

    private GoogleMap mMap;
    private Context context;
    LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
    private String currParticipant;
    private Database db = new Database();
    String dateformat = "MM/dd/yyyy HH:mm:ss";
    DateFormat dateF = new SimpleDateFormat(dateformat);

    /**
     *
     * this method acts as an init method which retrieves references to Users' Friends' Mood History on Map View
     * and Users' Mood History on Map View on screen by setting button onClickListener
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_mood_map);
        final Button myMoodBtn = findViewById(R.id.mood_map_my_map_btn); // button connected to user's mood history map view
        final Button friendMoodBtn = findViewById(R.id.mood_map_friend_map_btn); // button connected to user's friends' mood history map view

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mood_map_map_fragment);
        currParticipant = getIntent().getStringExtra("USERNAME"); // find the user's username
        mapFragment.getMapAsync(this);

        friendMoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* retrieve user's friends' information from database */
                db.retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(currParticipant,
                        (MoodMap) context);

                /* change button color onclick */
                friendMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                friendMoodBtn.setTextColor(Color.parseColor("#008577"));
                myMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008577")));
                myMoodBtn.setTextColor(Color.parseColor("#ffffff"));
            }
        });
        myMoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* retrieve user's information from databse */
                db.retrieveAllLocatedMoodEventsOfAParticipant(currParticipant,
                        (MoodMap)context);

                /* change button color onclick */
                myMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                myMoodBtn.setTextColor(Color.parseColor("#008577"));
                friendMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008577")));
                friendMoodBtn.setTextColor(Color.parseColor("#ffffff"));

            }
        });

    }


    /**
     * {@inheritDoc}
     * @param moodEventsWithLocation an array list which contains the user's information
     */

    @Override
    public void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation) {
        mMap.clear();
        IconGenerator iconGenerator = new IconGenerator(this); // To style the map marker
        for(MoodEvent each: moodEventsWithLocation) { // get user's emotion
            if (each.getEmoticon() != null) {
                GeoPoint location = each.getLocationOfMoodEvent();
                String date = dateF.format(each.getDate());
                Marker marker = mMap.addMarker(new MarkerOptions() // add a marker on map based on the geopoints recorded
                        .position(new LatLng(location.getLatitude(),
                                location.getLongitude()))
                        .title(date)
                        .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(each.getEmoticon())))); // change

                marker.showInfoWindow(); // Solve overlap icons problem

                latLngBounds.include(new LatLng(location.getLatitude(),
                        location.getLongitude())); // record markers

            }
        }

        // move camera to show the new markers
        if(latLngBounds!= null){
        LatLngBounds bounds = latLngBounds.build();
        CameraUpdate show_markers = CameraUpdateFactory.newLatLngBounds(bounds,
                    getResources().getDisplayMetrics().widthPixels,
                    getResources().getDisplayMetrics().heightPixels,
                    (int)((getResources().getDisplayMetrics().widthPixels)*0.2));  // determine where to set the camera based on markers
        mMap.animateCamera(show_markers, 3000, null);}

    }

    /**
     * {@inheritDoc}
     * @param message
     */

    @Override
    public void failRetrieveAllLocatedMoodEventsOfAParticipant(String message) {

    }

    /**
     * {@inheritDoc}
     * @param mostRecentMoodEventsOfFriendsOfAParticipantWithLocation an array list which contains the user's friends' information
     */

    @Override
    public void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
        mMap.clear();
        IconGenerator iconGenerator = new IconGenerator(this);
        for(MoodEvent each: mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
            if (each.getEmoticon() != null) {
                GeoPoint location = each.getLocationOfMoodEvent();
                String date = dateF.format(each.getDate());
                Marker marker = mMap.addMarker(new MarkerOptions() // add a marker on map based on the geopoints recorded
                        .position(new LatLng(location.getLatitude(),
                                location.getLongitude()))
                        .title(each.getUsername())
                        .snippet(date)
                        .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(each.getEmoticon()))));

                marker.showInfoWindow();

                latLngBounds.include(new LatLng(location.getLatitude(),
                        location.getLongitude())); // record markers

            }
        }
        // move camera to show the new markers
        if(latLngBounds!= null){
        LatLngBounds bounds = latLngBounds.build();
        CameraUpdate show_markers = CameraUpdateFactory.newLatLngBounds(bounds,
                getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels,
                (int)((getResources().getDisplayMetrics().widthPixels)*0.2)); // determine where to set the camera based on markers
        mMap.animateCamera(show_markers, 3000, null);}
    }

    /**
     * {@inheritDoc}
     * @param message
     */
    @Override
    public void failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String message) {

    }


    /**
     * {@inheritDoc}
     * @param googleMap a google map
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setIndoorEnabled(true); // Indoor Map
        googleMap.setMyLocationEnabled(false); // Location Sharing: not require for this screen
        UiSettings Ui = googleMap.getUiSettings();
        Ui.setZoomControlsEnabled(true); //Zoom in zoom out event

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap = googleMap;

        LatLng UofA = new LatLng(53.526777, -113.527153); // Latitude, Longitude

        CameraPosition UAlberta = CameraPosition.builder()
                .target(UofA)
                .zoom(5)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(UAlberta), 3000, null);
    }
}