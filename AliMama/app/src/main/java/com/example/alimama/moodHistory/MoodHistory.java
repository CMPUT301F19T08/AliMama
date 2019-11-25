package com.example.alimama.moodHistory;

import android.content.Intent;
import android.os.Bundle;

import com.example.alimama.addEditMood.AddEditMoodActivity;
import com.example.alimama.Model.MoodEvent;
import com.example.alimama.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a class that keeps track of a list of the MoodEvent objects and displays them using a recycler view
 */

public class MoodHistory extends AppCompatActivity implements MoodHistoryContract.View, MoodEventClickListener {

    private MoodHistoryPresenter presenter;
    private MoodHistoryAdapter adapter;

    private FloatingActionButton fab;
    private Button btnMyHistory;
    private Button btnFriendsHistory;
    private Spinner spEmoticon;
    private String currentLoggedInUser;

    // BEGIN - Android lifecycle methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        setupViews();
        setupButtonClickListeners();
        setupEmoticonsList();

        this.currentLoggedInUser = getIntent().getStringExtra("USERNAME");

        presenter = new MoodHistoryPresenter(this, currentLoggedInUser);

        spEmoticon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onMoodItemSelected(spEmoticon.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//<<<<<<< HEAD:AliMama/app/src/main/java/com/example/alimama/MoodHistory.java
//
//        recyclerView = findViewById(R.id.rvMoods);
//        adapter = new MoodHistoryAdapter(this);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(layoutManager);
//
//        database = new Database();
//
//        //Checks to see if the My History button is pressed so that it displays the mood history of the current logged on user.
//        btnMyHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CURRENT_STATE = STATE_MY_HISTORY;
//                btnMyHistory.setTextColor(getColor(R.color.colorPrimary));
//                btnFriendsHistory.setTextColor(getColor(R.color.colorPrimaryDark));
//                database.retrieveAllMoodEventsOfAParticipant(currLoggedInUser, MoodHistory.this);
//            }
//        });
//
//        //Checks to see if the Friends History button is pressed so that it displays the mood history of the current logged on users friends.
//        btnFriendsHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CURRENT_STATE = STATE_FRIENDS_HISTORY;
//                btnMyHistory.setTextColor(getColor(R.color.colorPrimaryDark));
//                btnFriendsHistory.setTextColor(getColor(R.color.colorPrimary));
//                database.retrieveMostRecentMoodEventOfFriendsOfAParticipant(currLoggedInUser, MoodHistory.this);
//            }
//        });
//
//        //Checks to see if the Add mood button is pressed so that a user can add a mood. It will switch to a new activity "AddEditMoodActivity"
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), AddEditMoodActivity.class);
//                intent.putExtra("USERNAME", currLoggedInUser);
//                startActivity(intent);
//            }
//        });
//=======
//>>>>>>> master:AliMama/app/src/main/java/com/example/alimama/moodHistory/MoodHistory.java
    }

    //Gets mood events from the database and sets the colors of the tabs to verify that we are on the tab that was selected.
    @Override
    protected void onResume() {
        super.onResume();
        presenter.onMoodHistoryViewResumed();
    }

    // END - Android lifecycle methods

    // BEGIN - MoodEventClickListener methods

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
        presenter.onMoodItemDeleteClicked(event);
    }

    // END - MoodEventClickListener methods


    // BEGIN - MoodHistoryContract.View methods

    @Override
    public void selectMyHistoryButton() {
        fab.show();
        btnMyHistory.setTextColor(getColor(R.color.colorPrimary));
        btnFriendsHistory.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void selectFriendsHistoryButton() {
        fab.hide();
        btnMyHistory.setTextColor(getColor(R.color.colorPrimaryDark));
        btnFriendsHistory.setTextColor(getColor(R.color.colorPrimary));
    }

    @Override
    public void showMoodEventsList(ArrayList<MoodEvent> moodEventHistory, String currentEmoticon, boolean disableDeleteButton) {
        adapter.setMoodEvents(moodEventHistory, currentEmoticon, disableDeleteButton);
    }

    // END - MoodHistoryContract.View methods


    // BEGIN - Helper methods

    private void setupViews() {
        fab = findViewById(R.id.fab);
        btnMyHistory = findViewById(R.id.btnMyHistory);
        btnFriendsHistory = findViewById(R.id.btnFriendsHistory);
        spEmoticon = findViewById(R.id.spEmoticon);

//<<<<<<< HEAD:AliMama/app/src/main/java/com/example/alimama/MoodHistory.java
    /**
     * When the edit button is pressed, we switch to a new activity: "AddEditMoodActivity". It takes in the previous inputs for the current
     * mood event and displays them on the edit screen so all the information can be viewed or edited.
     * @param event
     */
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

    /**
     * This function deletes the mood event of the current logged on user.
     * @param event
     */
    @Override
    public void onDeleteClick(MoodEvent event) {
        database.deleteAMoodEventOfAParticipant(event, this);
//=======
        RecyclerView recyclerView = findViewById(R.id.rvMoods);
        adapter = new MoodHistoryAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    private void setupButtonClickListeners() {
        btnMyHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onMyHistoryClicked();
            }
        });

        btnFriendsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFriendsHistoryButtonClicked();
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
//>>>>>>> master:AliMama/app/src/main/java/com/example/alimama/moodHistory/MoodHistory.java
    }


    //This function sets of the emoticon list that the user can use to filter by mood by choosing an emoticon
    private void setupEmoticonsList() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.emoticons_array_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmoticon.setAdapter(adapter);
    }

//<<<<<<< HEAD:AliMama/app/src/main/java/com/example/alimama/MoodHistory.java
    //Gets the mood events from the database depending on whether we are on the My History tab or the Friends History tab
    private void getMoodEvents() {
        if (CURRENT_STATE == STATE_MY_HISTORY) {
            database.retrieveAllMoodEventsOfAParticipant(this.currLoggedInUser, this);
        } else if (CURRENT_STATE == STATE_FRIENDS_HISTORY) {
            database.retrieveMostRecentMoodEventOfFriendsOfAParticipant(this.currLoggedInUser, this);
        }
    }
//=======
    // END - Helper methods

//>>>>>>> master:AliMama/app/src/main/java/com/example/alimama/moodHistory/MoodHistory.java
}