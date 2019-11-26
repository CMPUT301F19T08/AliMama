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
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onMoodHistoryViewResumed();
    }

    // END - Android lifecycle methods


    // BEGIN - MoodEventClickListener methods

    /**
     * This function provides information when the Edit Mood button is clicked.
     * @param event This is the current event.
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
     * This function remove the mood when the Delete button is clicked.
     * @param event This is the current event.
     */
    @Override
    public void onDeleteClick(MoodEvent event) {
        presenter.onMoodItemDeleteClicked(event);
    }

    // END - MoodEventClickListener methods


    // BEGIN - MoodHistoryContract.View methods

    /**
     * This controls the view when the My History Button is clicked.
     */
    @Override
    public void selectMyHistoryButton() {
        fab.show();
        btnMyHistory.setTextColor(getColor(R.color.colorPrimary));
        btnFriendsHistory.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    /**
     * This controls the view when the Friend History Button is clicked.
     */
    @Override
    public void selectFriendsHistoryButton() {
        fab.hide();
        btnMyHistory.setTextColor(getColor(R.color.colorPrimaryDark));
        btnFriendsHistory.setTextColor(getColor(R.color.colorPrimary));
    }

    /**
     * This controls the recycler view list.
     * @param moodEventHistory This is the Mood Events Arrays that needs to be displayed.
     * @param currentEmoticon This is the sorted emoticon.
     * @param disableDeleteButton Whether edition or deletion is allowed or not.
     */
    @Override
    public void showMoodEventsList(ArrayList<MoodEvent> moodEventHistory, String currentEmoticon, boolean disableDeleteButton) {
        adapter.setMoodEvents(moodEventHistory, currentEmoticon, disableDeleteButton);
    }

    // END - MoodHistoryContract.View methods


    // BEGIN - Helper methods

    /**
     * This function sets up the view.
     */
    private void setupViews() {
        fab = findViewById(R.id.fab);
        btnMyHistory = findViewById(R.id.btnMyHistory);
        btnFriendsHistory = findViewById(R.id.btnFriendsHistory);
        spEmoticon = findViewById(R.id.spEmoticon);

        RecyclerView recyclerView = findViewById(R.id.rvMoods);
        adapter = new MoodHistoryAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    /**
     * This function manages when the My History button is clicked.
     */
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
    }

    /**
     * This function sets up the RecyclerView array.
     */
    private void setupEmoticonsList() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.emoticons_array_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmoticon.setAdapter(adapter);
    }

    // END - Helper methods

}