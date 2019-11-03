package com.example.alimama;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.alimama.ui.main.SectionsPagerAdapter;
import java.util.ArrayList;
import java.util.Arrays;

public class MoodHistory extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    FloatingActionButton fab;
    static final String happy = new String(Character.toChars(0x1F60A));
    static final String tears = new String(Character.toChars(0X1F602));
    static final String heart = new String(Character.toChars(0x1F60D));
    static final String angry = new String(Character.toChars(0x1F621));
    static final String tongue = new String(Character.toChars(0x1F61C));
    static final String cry = new String(Character.toChars(0x1F622));
    static final String smirk = new String(Character.toChars(0x1F60F));
    private static final String[] paths = {happy, tears, heart, angry, tongue, cry, smirk};

    private RecyclerView.Adapter mAdapter;
    private ArrayList<MoodEvent> moodEvents;
    private Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddMood = new Intent(MoodHistory.this, AddMoodEvent.class);
                startActivity(AddMood);
            }
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        moodEvents = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(moodEvents, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        //mResources = getResources();
        // set onClickListener for addNewRideBtn
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Bundle bundle = new Bundle();
//                int requestCode = mResources.getInteger(R.integer.ADD_NEW_RIDE);
//                bundle.putInt(mResources.getString(R.string.intent_key_request_code),requestCode);
//                Intent intent = new Intent(view.getContext(), AddUpdateRideActivity.class);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, requestCode);

                Intent AddMood = new Intent(MoodHistory.this, AddMoodEvent.class);
                startActivity(AddMood);

            }
        });



    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            // Make sure the request was successful
//            Bundle bundle = data.getExtras();
//            String date = bundle.getString(mResources.getString(R.string.intent_key_date));
//            String time = bundle.getString(mResources.getString(R.string.intent_key_time));
//            Float distance = Float.valueOf(bundle.getString(mResources.getString(R.string.intent_key_distance)));
//            Float avgSpeed = Float.valueOf(bundle.getString(mResources.getString(R.string.intent_key_avg_speed)));
//            Integer avgCadence = Integer.valueOf(bundle.getString(mResources.getString(R.string.intent_key_avg_cadence)));
//            String comments = bundle.getString(mResources.getString(R.string.intent_key_comments));
//            MoodEvent newRide = new MoodEvent(username, emotionalState, date, time);
//            if (requestCode == mResources.getInteger(R.integer.ADD_NEW_RIDE)) {
//                this.rides.add(newRide);
//            } else if (requestCode == mResources.getInteger(R.integer.UPDATE_RIDE)) {
//                int itemPosition = bundle.getInt(mResources.getString(R.string.intent_key_item_position));
//                this.rides.set(itemPosition, newRide);
//            }
//            this.mAdapter.notifyDataSetChanged();
//
//        }
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

//    @Override
//    public void onUpdateMoodBtnClick(int position) {
//        Intent intent = new Intent(this, AddMoodEvent.class);
//        Bundle bundle = new Bundle();
//        MoodEvent moodevent = this.moodEvents.get(position);
//        bundle.putInt(mResources.getString(R.string.intent_key_item_position), position);
//        bundle.putString(mResources.getString(R.string.intent_key_date), ride.getDate());
//        bundle.putString(mResources.getString(R.string.intent_key_time), ride.getTime());
//        bundle.putString(mResources.getString(R.string.intent_key_distance), String.valueOf(ride.getDistance()));
//        bundle.putString(mResources.getString(R.string.intent_key_avg_speed), String.valueOf(ride.getAvgSpeed()));
//        bundle.putString(mResources.getString(R.string.intent_key_avg_cadence), String.valueOf(ride.getAvgCadence()));
//        bundle.putString(mResources.getString(R.string.intent_key_comments), ride.getComments());
//
//        bundle.putInt(mResources.getString(R.string.intent_key_request_code), mResources.getInteger(R.integer.UPDATE_RIDE));
//        intent.putExtras(bundle);
//
//        startActivityForResult(intent, mResources.getInteger(R.integer.UPDATE_RIDE));
//
//    }
//
//    @Override
//    public void onDeleteMoodBtnClick(int position) {
//        moodEvents.remove(position);
//        this.mAdapter.notifyDataSetChanged();
//    }

}