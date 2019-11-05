package com.example.alimama;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.alimama.MoodMapUI.SectionsPagerAdapter;

import java.util.ArrayList;

public class MoodMap extends AppCompatActivity implements MapViewFeedback{

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private SectionsPagerAdapter sectionsPagerAdapter;

    // tab event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_map);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }

    @Override
    public void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation) {

    }

    @Override
    public void failRetrieveAllLocatedMoodEventsOfAParticipant(String message) {

    }

    @Override
    public void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
        MyFriendMoodMap myFriendMoodMap = (MyFriendMoodMap) this.sectionsPagerAdapter.getItem(1);
        myFriendMoodMap.setMapData(mostRecentMoodEventsOfFriendsOfAParticipantWithLocation);



    }

    @Override
    public void failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String message) {

    }
}