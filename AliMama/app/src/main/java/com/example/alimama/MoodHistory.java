package com.example.alimama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alimama.Model.MoodEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a class that keeps track of a list of the MoodEvent objects and displays them using a recycler view
 */

public class MoodHistory extends AppCompatActivity implements MoodEventManipulationFeedback, MoodEventClickListener {

    private final int STATE_MY_HISTORY = 0;
    private final int STATE_FRIENDS_HISTORY = 1;
    private int CURRENT_STATE = 0;
    private Database database;
    private String currLoggedInUser;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MoodHistoryAdapter adapter;
    private Button btnMyHistory;
    private Button btnFriendsHistory;
    private Spinner spEmoticon;
    private String currentEmoticon = "\uD83D\uDE0A"; //happy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        currLoggedInUser = getIntent().getStringExtra("USERNAME");
        FloatingActionButton fab = findViewById(R.id.fab);
        btnMyHistory = findViewById(R.id.btnMyHistory);
        btnFriendsHistory = findViewById(R.id.btnFriendsHistory);
        spEmoticon = findViewById(R.id.spEmoticon);
        this.currLoggedInUser = getIntent().getStringExtra("USERNAME");

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

        database = new Database();

        //Checks to see if the My History button is pressed so that it displays the mood history of the current logged on user.
        btnMyHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_STATE = STATE_MY_HISTORY;
                btnMyHistory.setTextColor(getColor(R.color.colorPrimary));
                btnFriendsHistory.setTextColor(getColor(R.color.colorPrimaryDark));
                database.retrieveAllMoodEventsOfAParticipant(currLoggedInUser, MoodHistory.this);
            }
        });

        //Checks to see if the Friends History button is pressed so that it displays the mood history of the current logged on users friends.
        btnFriendsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_STATE = STATE_FRIENDS_HISTORY;
                btnMyHistory.setTextColor(getColor(R.color.colorPrimaryDark));
                btnFriendsHistory.setTextColor(getColor(R.color.colorPrimary));
                database.retrieveMostRecentMoodEventOfFriendsOfAParticipant(currLoggedInUser, MoodHistory.this);
            }
        });

        //Checks to see if the Add mood button is pressed so that a user can add a mood. It will switch to a new activity "AddEditMoodActivity"
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddEditMoodActivity.class);
                intent.putExtra("USERNAME", currLoggedInUser);
                startActivity(intent);
            }
        });
    }

    //Gets mood events from the database and sets the colors of the tabs to verify that we are on the tab that was selected.
    @Override
    protected void onResume() {
        super.onResume();
        btnMyHistory.setTextColor(getColor(R.color.colorPrimary));
        btnFriendsHistory.setTextColor(getColor(R.color.colorPrimaryDark));
        getMoodEvents();
    }

    // The following functions checks to see if updating or adding a mood event, or retrieving a mood event is successful or failed.
    // If failed them then an error message is printed
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
        database.retrieveAllMoodEventsOfAParticipant(currLoggedInUser, MoodHistory.this);
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
    }


    //This function sets of the emoticon list that the user can use to filter by mood by choosing an emoticon
    private void setupEmoticonsList() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.emoticons_array_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmoticon.setAdapter(adapter);
    }

    //Gets the mood events from the database depending on whether we are on the My History tab or the Friends History tab
    private void getMoodEvents() {
        if (CURRENT_STATE == STATE_MY_HISTORY) {
            database.retrieveAllMoodEventsOfAParticipant(this.currLoggedInUser, this);
        } else if (CURRENT_STATE == STATE_FRIENDS_HISTORY) {
            database.retrieveMostRecentMoodEventOfFriendsOfAParticipant(this.currLoggedInUser, this);
        }
    }
}