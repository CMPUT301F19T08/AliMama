package com.example.alimama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Mainmenu extends AppCompatActivity {

    Button viewMoodHistoryButton;
    Button viewMoodMapButton;
    Button viewOrAddFriendsButton;
    // delete the following button before submit code
    Button viewGoogleMapButton;
    Button logoutButton;
    String loggedInParticipant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);


        loggedInParticipant  = getIntent().getStringExtra("USERNAME");
        viewMoodHistoryButton = findViewById(R.id.main_menu_mood_history_button);
        viewMoodMapButton = findViewById(R.id.main_menu_mood_map_button);
        viewOrAddFriendsButton = findViewById(R.id.main_menu_add_view_friend_button);
        logoutButton = findViewById(R.id.main_menu_logout_button);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        viewMoodHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMoodHistory = new Intent(Mainmenu.this, MoodHistory.class);
                goToMoodHistory.putExtra("USERNAME", loggedInParticipant);
                startActivity(goToMoodHistory);
            }
        });

        viewMoodMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMoodMap = new Intent(Mainmenu.this, MoodMap.class);
                goToMoodMap.putExtra("USERNAME", loggedInParticipant);
                startActivity(goToMoodMap);
            }
        });

        viewOrAddFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToViewAddFriend = new Intent(Mainmenu.this, FriendPage.class);
                goToViewAddFriend.putExtra("USERNAME", loggedInParticipant);
                startActivity(goToViewAddFriend);
            }
        });

    }
}
