package com.example.alimama;

import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

//Reference: https://demonuts.com/android-google-map-in-fragment/
public class MyMoodMap extends Fragment{

    // DatabaseReference users;
    private GoogleMap mMap;
    Marker marker;
    private Database db = new Database();

    public MyMoodMap() {
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
        View rootView = inflater.inflate(R.layout.activity_my_mood_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment

        /* // reference to a user from database
        users = FirebaseDatabase.getInstance().getReference("username");
        users.push().setValue(marker); */

        mapFragment.getMapAsync(new OnMapReadyCallback() {
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

                // mMap.clear(); //clear old markers

               /* // retrieve data from database
                users.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        for(DataSnapshot s:dataSnapshot.getChildren()){
                            MoodEvent user=s.getValue(MoodEvent.class);
                            GeoPoint location= user.getLocationOfMoodEvent();
                            double lat = location.getLatitude();
                            double lng = location.getLongitude();
                            LatLng latLng = new LatLng(lat,lng);
                            System.out.println(lat);
                            System.out.println(lng);
                            mMap.addMarker(new MarkerOptions().position(latLng));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError){

                    }
                }); */

            }
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
        });


        return rootView;
    }

}