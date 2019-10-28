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
    Button logoutButton;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        viewMoodHistoryButton=(Button)findViewById(R.id.mood_history_button);
        viewMoodMapButton=(Button)findViewById(R.id.mood_map_button);
        viewOrAddFriendsButton=(Button)findViewById(R.id.add_view_friend_button);
        logoutButton=(Button)findViewById(R.id.logout_button);

        viewMoodHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMoodHistory = new Intent(Mainmenu.this, MoodHistory.class);
                startActivity(goToMoodHistory);
            }
        });

        viewMoodMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMoodMap = new Intent(Mainmenu.this, MoodMap.class);
                startActivity(goToMoodMap);
            }
        });

        viewOrAddFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToFriendsList = new Intent(Mainmenu.this, FriendsList.class);
                startActivity(goToFriendsList);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                database.auth().signOut().then(function() {
//                    console.log('Signed Out');
//                };

                finish();
                Intent i=new Intent(Mainmenu.this, ParticipantLoginSignupActivity.class);
                i.putExtra("finish", true);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                //startActivity(i);
                startActivity( i );
                database.getInstance().signOut();
            }
        });








    }
}
