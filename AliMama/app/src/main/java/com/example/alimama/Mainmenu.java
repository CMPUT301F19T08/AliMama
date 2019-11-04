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
    Button logoutButton;
    private Database database;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        viewMoodHistoryButton = findViewById(R.id.mood_history_button);
        viewMoodMapButton = findViewById(R.id.mood_map_button);
        viewOrAddFriendsButton = findViewById(R.id.add_view_friend_button);
        logoutButton = findViewById(R.id.logout_button);
        Button button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editMood = new Intent(Mainmenu.this, EditMoodEvent.class);
                startActivity(editMood);
            }
        });

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

        firebaseAuth = FirebaseAuth.getInstance();
        logoutButton=(Button)findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                database.auth().signOut().then(function() {
//                    console.log('Signed Out');
//                };

                firebaseAuth.signOut();
                finish();
                Intent logOut=new Intent(Mainmenu.this, ParticipantLoginSignupActivity.class);
                //i.putExtra("finish", true);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                //startActivity(i);
                startActivity( logOut );
            }
        });

        viewOrAddFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToFriendPage = new Intent(Mainmenu.this, FriendPage.class);
                startActivity(goToFriendPage);
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


}
