package com.example.alimama;

import android.content.Intent;
import android.os.Bundle;

import com.example.alimama.Model.MoodEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import java.util.ArrayList;

public class MoodHistory extends AppCompatActivity implements MoodEventManipulationFeedback, MoodEventClickListener {

    private final int STATE_MY_HISTORY = 0;
    private final int STATE_FRIENDS_HISTORY = 1;
    private int CURRENT_STATE = 0;

    private DatabaseUtil mDatabaseUtil;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MoodHistoryAdapter adapter;

    private Button btnMyHistory;
    private Button btnFriendsHistory;
    private Spinner spEmoticon;
    private String currentEmoticon = "\uD83D\uDE0A"; //happy
    private String currentLoggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);
        final FloatingActionButton fab = findViewById(R.id.fab);
        btnMyHistory = findViewById(R.id.btnMyHistory);
        btnFriendsHistory = findViewById(R.id.btnFriendsHistory);
        spEmoticon = findViewById(R.id.spEmoticon);
        this.currentLoggedInUser = getIntent().getStringExtra("USERNAME");

        setupEmoticonsList();

        spEmoticon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentEmoticon = spEmoticon.getSelectedItem().toString();
                getMoodEvents();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView = findViewById(R.id.rvMoods);
        adapter = new MoodHistoryAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        mDatabaseUtil = new DatabaseUtil();

        btnMyHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_STATE = STATE_MY_HISTORY;
                btnMyHistory.setTextColor(getColor(R.color.colorPrimary));
                btnFriendsHistory.setTextColor(getColor(R.color.colorPrimaryDark));
                mDatabaseUtil.retrieveAllMoodEventsOfAParticipant(currentLoggedInUser, MoodHistory.this);
                fab.show();
            }
        });

        btnFriendsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_STATE = STATE_FRIENDS_HISTORY;
                btnMyHistory.setTextColor(getColor(R.color.colorPrimaryDark));
                btnFriendsHistory.setTextColor(getColor(R.color.colorPrimary));
                mDatabaseUtil.retrieveMostRecentMoodEventOfFriendsOfAParticipant(currentLoggedInUser, MoodHistory.this);
                fab.hide();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddEditMoodActivity.class);
                intent.putExtra("USERNAME", currentLoggedInUser);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnMyHistory.setTextColor(getColor(R.color.colorPrimary));
        btnFriendsHistory.setTextColor(getColor(R.color.colorPrimaryDark));
        getMoodEvents();


    }

    @Override
    public void failToUpdateAnExistingMoodEvent(String errmsg) {

    }

    @Override
    public void updateAnExistingMoodEventSuccessfully() {

    }

    @Override
    public void failToAddANewMoodEvent(String errmsg) {

    }

    @Override
    public void addANewMoodEventSuccessfully() {

    }

    @Override
    public void retrieveAllMoodEventOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventHistory) {
        adapter.setMoodEvents(moodEventHistory, currentEmoticon);
    }

    @Override
    public void failToRetrieveAllMoodEventOfAParticipant(String errmsg) {

    }

    @Override
    public void deleteAMoodEventOfAParticipantSuccessfully() {
        mDatabaseUtil.retrieveAllMoodEventsOfAParticipant(this.currentLoggedInUser, MoodHistory.this);
    }

    @Override
    public void failToDeleteAMoodEventOfAParticipant(String errmsg) {

    }

    @Override
    public void failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(String message) {

    }

    @Override
    public void retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipant) {
        adapter.setMoodEvents(mostRecentMoodEventsOfFriendsOfAParticipant, currentEmoticon);

    }

    @Override
    public void failRegisterMoodEventRealTimeListener(String message) {

    }

    @Override
    public void onEditClick(MoodEvent event) {
        Intent intent = new Intent(this, AddEditMoodActivity.class);
        intent.putExtra(AddEditMoodActivity.EXTRA_DOCUMENT_ID, event.getDocumentId());
        intent.putExtra(AddEditMoodActivity.EXTRA_DATE, event.getDate().toString());
        intent.putExtra(AddEditMoodActivity.EXTRA_EMOTIONAL_STATE, event.getEmotionalState());
        intent.putExtra(AddEditMoodActivity.EXTRA_USERNAME, event.getUsername());
        intent.putExtra(AddEditMoodActivity.EXTRA_DESCRIPTION, event.getReasonInText());
        intent.putExtra(AddEditMoodActivity.EXTRA_PHOTO_PATH, event.getPathToPhoto());
        if (event.getLocationOfMoodEvent() != null) {
            intent.putExtra(AddEditMoodActivity.EXTRA_LOCATION_LAT, event.getLocationOfMoodEvent().getLatitude());
            intent.putExtra(AddEditMoodActivity.EXTRA_LOCATION_LNG, event.getLocationOfMoodEvent().getLongitude());
        }
        intent.putExtra(AddEditMoodActivity.EXTRA_EMOTICON, event.getEmoticon());
        intent.putExtra(AddEditMoodActivity.EXTRA_SOCIAL_SITUATION, event.getSocialSituation());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(MoodEvent event) {
        mDatabaseUtil.deleteAMoodEventOfAParticipant(event, this);
    }

    private void setupEmoticonsList() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.emoticons_array_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmoticon.setAdapter(adapter);
    }

    private void getMoodEvents() {
        if (CURRENT_STATE == STATE_MY_HISTORY) {
            mDatabaseUtil.retrieveAllMoodEventsOfAParticipant(this.currentLoggedInUser, this);
        } else if (CURRENT_STATE == STATE_FRIENDS_HISTORY) {
            mDatabaseUtil.retrieveMostRecentMoodEventOfFriendsOfAParticipant(this.currentLoggedInUser, this);
        }
    }

}