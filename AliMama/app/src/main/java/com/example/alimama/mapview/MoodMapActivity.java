
package com.example.alimama.mapview;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alimama.R;
import com.google.android.gms.maps.CameraUpdate;
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
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;

/**
 * MoodMapActivity
 * Version 1.0
 * 06 November 2019
 * Copyright: UAlberta CMPUT301Fall2019 Group 8
 * This class displays located MoodEvent of both current logged-in participant and friends' of the participant on Google Map
 * this class implements MoodMapContract.MoodMapView interface to conform Model-View-Presenter design pattern
 * No outstanding issues identified
 */
public class MoodMapActivity extends AppCompatActivity implements MoodMapContract.MoodMapView, OnMapReadyCallback{

    private GoogleMap mMap;
    LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
    private MoodMapPresenter mMoodMapPresenter;

    /**
     *
     * this method acts as an init method which retrieves references to Participants' Friends' Mood History on Map View
     * and Participants' Mood History on Map View on screen by setting button onClickListener
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mood_map);
        final Button myMoodBtn = findViewById(R.id.mood_map_my_map_btn); // button connected to user's mood history map view
        final Button friendMoodBtn = findViewById(R.id.mood_map_friend_map_btn); // button connected to user's friends' mood history map view
        this.mMoodMapPresenter = new MoodMapPresenter(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mood_map_map_fragment);
        this.mMoodMapPresenter.setCurrentLoggedInParticipant(getIntent().getStringExtra("USERNAME")); // find the user's username
        mapFragment.getMapAsync(this);

        friendMoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMoodMapPresenter.retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant();
                /* change button color onclick */
                /*friendMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                friendMoodBtn.setTextColor(Color.parseColor("#008577"));
                myMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008577")));
                myMoodBtn.setTextColor(Color.parseColor("#ffffff"));*/
            }
        });
        myMoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMoodMapPresenter.retrieveAllLocatedMoodEventsOfAParticipant();
                /* change button color onclick */
                /*myMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                myMoodBtn.setTextColor(Color.parseColor("#008577"));
                friendMoodBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008577")));
                friendMoodBtn.setTextColor(Color.parseColor("#ffffff"));*/

            }
        });

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


    /**
     * This method clears all marker currently displayed on GoogleMap
     * */
    @Override
    public void clearMap() {
        mMap.clear();
    }

    /**
     * this method constructs a new GoogleMap Marker given parameters
     * @param username username to be displayed on the marker
     * @param latitude latitude to be displayed on the marker
     * @param longitude longitude to be displayed on the marker
     * @param emoticon emoticon to be displayed on the marker
     * */
    @Override
    public void createMarkerforUsers(String username, double latitude, double longitude, String emoticon) {
        IconGenerator iconGenerator = new IconGenerator(this);
        Marker marker = mMap.addMarker(new MarkerOptions() // add a marker on map based on the geopoints recorded
                .position(new LatLng(latitude,
                        longitude))

                .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(emoticon))));

        marker.hideInfoWindow();
        latLngBounds.include(new LatLng(latitude,
               longitude)); // record markers
        moveCameraToNewMarker();
    }

    /**
     * this method constructs a new GoogleMap Marker given parameters
     * @param username username to be displayed on the marker
     * @param latitude latitude to be displayed on the marker
     * @param longitude longitude to be displayed on the marker
     * @param emoticon emoticon to be displayed on the marker
     * */

    @Override
    public void createMarkerforFriends(String username, double latitude, double longitude, String emoticon) {

        ArrayList<String> mapList = new ArrayList<>();
        mapList.add(emoticon);
        mapList.add(username);
        String string = "";

        for (String s : mapList)
        {
            string += s + "\n";
        }

        String icon = string.trim();

        IconGenerator iconGenerator = new IconGenerator(this);
        Marker marker = mMap.addMarker(new MarkerOptions() // add a marker on map based on the geopoints recorded
                .position(new LatLng(latitude,
                        longitude))

                .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(icon))));

        marker.hideInfoWindow();

        latLngBounds.include(new LatLng(latitude,
                longitude)); // record markers
        moveCameraToNewMarker();
    }

    /**
     * this method displays a Toast showing the error message
     * @param error the error message
     * */
    @Override
    public void displayLocatedMoodEventDatabaseErrorToast(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }

    /**
     * this method moves camera focus towards newly added GoogleMap marker
     * */
    private void moveCameraToNewMarker() {
        // move camera to show the new markers
        try{
            LatLngBounds bounds = latLngBounds.build();
            CameraUpdate show_markers = CameraUpdateFactory.newLatLngBounds(bounds,
                    getResources().getDisplayMetrics().widthPixels,
                    getResources().getDisplayMetrics().heightPixels,
                    (int)((getResources().getDisplayMetrics().widthPixels)*0.2)); // determine where to set the camera based on markers
            mMap.animateCamera(show_markers, 3000, null);}
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}