package com.example.alimama.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alimama.FriendPageActivity;
import com.example.alimama.MoodHistory;
import com.example.alimama.mapview.MoodMapActivity;
import com.example.alimama.R;


/**
 * This activity is the entrance to all other screens of the applications
 * this class implements HomeScreenContract.HomeScreenView interface to conform Model-View-Presenter design pattern
 * No outstanding issues identified
 * */
public class HomeScreenActivity extends AppCompatActivity implements HomeScreenContract.HomeScreenView {

    Button viewMoodHistoryButton;
    Button viewMoodMapButton;
    Button viewOrAddFriendsButton;
    Button logoutButton;
    private HomeScreenPresenter mHomeScreenPresenter;


    /**
     *
     * this method acts as an init method which retrieves all references to Views on screen as well as setting on
     * button onClickListener
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        viewMoodHistoryButton = findViewById(R.id.main_menu_mood_history_button);
        viewMoodMapButton = findViewById(R.id.main_menu_mood_map_button);
        viewOrAddFriendsButton = findViewById(R.id.main_menu_add_view_friend_button);
        logoutButton = findViewById(R.id.main_menu_logout_button);
        this.mHomeScreenPresenter = new HomeScreenPresenter(this);
        this.mHomeScreenPresenter.setCurrentLoggedInParticipant(getIntent().getStringExtra("USERNAME"));
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomeScreenPresenter.logoutParticipant();
            }
        });

        viewMoodHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomeScreenPresenter.gotoViewMoodHistoryScreen();
            }
        });
        viewMoodMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomeScreenPresenter.gotoViewMoodMapScreen();
            }
        });
        viewOrAddFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomeScreenPresenter.gotoViewOrAddFriendScreen();
            }
        });

    }

    /**
     * this method will switch the application back to participant login screen
     * */
    @Override
    public void startParticipantLoginScreen() {
        finish();

    }


    /**
     * this method will switch the application to view MoodHistory Screen
     * @param currentLoggedinParticipant participant information to be sent along intent
     * */
    @Override
    public void startViewMoodHistoryScreen(String currentLoggedinParticipant) {
        Intent goToMoodHistory = new Intent(HomeScreenActivity.this, MoodHistory.class);
        goToMoodHistory.putExtra("USERNAME", currentLoggedinParticipant);
        startActivity(goToMoodHistory);

    }

    /**
     * this method will switch the application to view or add friend Screen
     * @param currentLoggedinParticipant participant information to be sent along intent
     * */
    @Override
    public void startViewOrAddFriendScreen(String currentLoggedinParticipant) {
        Intent goToViewAddFriend = new Intent(HomeScreenActivity.this, FriendPageActivity.class);
        goToViewAddFriend.putExtra("USERNAME", currentLoggedinParticipant);
        startActivity(goToViewAddFriend);

    }

    /**
     * this method will switch the application to view MoodMap Screen
     * @param currentLoggedinParticipant participant information to be sent along intent
     * */
    @Override
    public void startViewMoodMapScreen(String currentLoggedinParticipant) {
        Intent goToMoodMap = new Intent(HomeScreenActivity.this, MoodMapActivity.class);
        goToMoodMap.putExtra("USERNAME", currentLoggedinParticipant);
        startActivity(goToMoodMap);

    }
}