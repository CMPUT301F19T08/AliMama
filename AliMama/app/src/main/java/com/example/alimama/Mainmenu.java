package com.example.alimama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Mainmenu extends AppCompatActivity {

    Button viewMoodHistoryButton;
    Button viewMoodMapButton;
    Button viewOrAddFriendsButton;
    // delete the following button before submit code
    Button viewGoogleMapButton;
    Button logoutButton;
    private Database database;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        viewMoodHistoryButton=(Button)findViewById(R.id.mood_history_button);
        viewMoodMapButton=(Button)findViewById(R.id.mood_map_button);
        viewOrAddFriendsButton=(Button)findViewById(R.id.add_view_friend_button);
        // delete the following button before submit code
        viewGoogleMapButton=(Button)findViewById(R.id.googlemap_button);

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

        //delete the code below
        viewGoogleMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMoodMap = new Intent(Mainmenu.this, GoogleMaps.class);
                startActivity(goToMoodMap);
            }
        });
//
//        viewOrAddFriendsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent goToFriendsList = new Intent(Mainmenu.this, FriendsList.class);
//                startActivity(goToFriendsList);
//            }
//        });

//        firebaseAuth = FirebaseAuth.getInstance();
//        logoutButton=(Button)findViewById(R.id.logout_button);
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                database.auth().signOut().then(function() {
////                    console.log('Signed Out');
////                };
//
//                firebaseAuth.signOut();
//                finish();
//                Intent logOut=new Intent(Mainmenu.this, ParticipantLoginSignupActivity.class);
//                //i.putExtra("finish", true);
//                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
//                //startActivity(i);
//                startActivity( logOut );
//
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
}
