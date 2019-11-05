package com.example.alimama;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

//Reference: https://demonuts.com/android-google-map-in-fragment/
public class MyFriendMoodMap extends Fragment {
    // DatabaseReference users;
    Marker marker;
    private Database db;
    private GoogleMap mMap;

    public MyFriendMoodMap() {
        // Required empty public constructor
    }

    // To show map on the screen
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.acitivity_friends_mood_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment

        // get user from database
        // users = FirebaseDatabase.getInstance().getReference("username");

        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

                mMap.setIndoorEnabled(true); // Indoor Map
                mMap.setMyLocationEnabled(false); // Location Sharing
                UiSettings Ui = mMap.getUiSettings();
                Ui.setZoomControlsEnabled(true); //Zoom in zoom out event
                Ui.setMapToolbarEnabled(false);
                Ui.setMyLocationButtonEnabled(false);

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                // Default location
               /* LatLng UofA = new LatLng(53.526777, -113.527153); // Latitude, Longitude
                mMap.addMarker(new MarkerOptions().position(UofA));

                CameraPosition UAlberta = CameraPosition.builder()
                        .target(UofA)
                        .zoom(10)
                        .bearing(0)
                        .tilt(45)
                        .build();

                // Animation
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(UAlberta), 3000, null);

                */
                LatLng UofA = new LatLng(53.526777, -113.527153); // Latitude, Longitude
                mMap.addMarker(new MarkerOptions().position(UofA).title("Happy"));
                CameraPosition UAlberta = CameraPosition.builder()
                        .target(UofA)
                        .zoom(15)
                        .bearing(0)
                        .tilt(45)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(UAlberta), 3000, null);
                mMap.addMarker(new MarkerOptions().position(new LatLng(53.528570, -113.524359)).title("Sad"));
                mMap.addMarker(new MarkerOptions().position(new LatLng(53.527647, -113.524210)).title("Angry"));
                mMap.addMarker(new MarkerOptions().position(new LatLng(53.526709, -113.521721)).title("Excited"));
                mMap.addMarker(new MarkerOptions().position(new LatLng(53.526658, -113.524714)).title("Happy"));
                mMap.addMarker(new MarkerOptions().position(new LatLng(53.526051, -113.526211)).title("Disappointment"));


            }
        });

        this.db = new Database();

        return rootView;
    }

    // Connecting to database - may leave to Part 4
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MoodMap moodMap = (MoodMap) getContext();
        this.db.retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant("xhou1", moodMap );
    }

    public void setMapData(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
        for (MoodEvent each: mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
            GeoPoint location = each.getLocationOfMoodEvent();
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            LatLng latLng = new LatLng(lat,lng);
            //   mMap.addMarker(new MarkerOptions().position(latLng));

        }
    }

}