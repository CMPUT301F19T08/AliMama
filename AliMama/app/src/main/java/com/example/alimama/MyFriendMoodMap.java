package com.example.alimama;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static androidx.core.content.ContextCompat.getSystemService;

//Reference: https://demonuts.com/android-google-map-in-fragment/
public class MyFriendMoodMap extends Fragment {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    LocationManager locationManager;

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
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                mMap.setIndoorEnabled(true); // Indoor Map
                mMap.setMyLocationEnabled(true); // Location Sharing
                UiSettings Ui = mMap.getUiSettings();
                Ui.setZoomControlsEnabled(true); //Zoom in zoom out event
                Ui.setMapToolbarEnabled(false);
                Ui.setMyLocationButtonEnabled(true);

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

            }
        });

        return rootView;
    }

}
