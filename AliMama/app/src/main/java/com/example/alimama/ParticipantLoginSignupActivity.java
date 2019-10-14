package com.example.alimama;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ParticipantLoginSignupActivity extends AppCompatActivity implements CredentialValidationDelegate {


    private TextInputLayout mUsername;
    private TextInputLayout mPassword;
    private Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_login_signup);
        init();


    }

    // examples of using Database MapView API
    private void testMapViewOperation() {
        TestMapViewOperation test = new TestMapViewOperation();
        test.retrieveAllLocatedMoodEventsOfAParticipant("xhou1");
        test.retrieveAllLocatedMoodEventsOfFriendsOfAParticipant("xhou1");
    }
    // examples of using Database Friendship Operation API
    private void  testFriendshipOperation() {
        TestFriendshipOperation test = new TestFriendshipOperation();
        test.sendFriendRequestFromCurrentParticipant("xhou1", "xhou2");
        test.sendFriendRequestFromCurrentParticipant("xhou3", "xhou2");
        test.sendFriendRequestFromCurrentParticipant("xhou4", "xhou2");
        test.sendFriendRequestFromCurrentParticipant("xhou6", "xhou2");
        test.sendFriendRequestFromCurrentParticipant("xhou7", "xhou2");
        test.sendFriendRequestFromCurrentParticipant("xhou1", "xhou3");
        test.sendFriendRequestFromCurrentParticipant("xhou1", "xhou4");
        test.sendFriendRequestFromCurrentParticipant("xhou1", "xhou5");

        test.acceptAFriendRequestOfAParticipant("xhou1", "xhou2");
        test.acceptAFriendRequestOfAParticipant("xhou1", "xhou3");
        test.acceptAFriendRequestOfAParticipant("xhou1", "xhou4");

        //test.retrieveAListOfParticipantsToAdd("xhou1");
        //test.retrieveAListOfParticipantsToAdd("xhou1");

    }

    // example of using Database MoodEventManipulation API
    private void testMoodEventManipulation() {
        TestMoodEventManipulation test = new TestMoodEventManipulation();
        test.registerMoodEventRealTimeListener("xhou1");
        MoodEvent m1 = new MoodEvent("xhou1", "sad", "alone", "I cant finish my assignment");
        MoodEvent m2 = new MoodEvent("xhou2", "cry", "alone", "I cant finish my assignment");
        MoodEvent m3 = new MoodEvent("xhou3", "happy", "alone", "I cant finish my assignment");
        MoodEvent m4 = new MoodEvent("xhou4", "I am to be deleted", "alone", "I cant finish my assignment");
        MoodEvent m5 = new MoodEvent("xhou1", "salty", "alone", "I cant finish my assignment");
        MoodEvent m6 = new MoodEvent("xhou2", "excited", "alone", "I cant finish my assignment");
        MoodEvent m7 = new MoodEvent("xhou3", "angry", "alone", "I cant finish my assignment");
        MoodEvent m8 = new MoodEvent("xhou4", "painful", "alone", "I cant finish my assignment");
        test.addMood(m1);
        test.addMood(m2);
        test.addMood(m3);
        test.addMood(m4);
        test.addMood(m5);
        test.addMood(m6);
        test.addMood(m7);
        test.addMood(m8);

        //test.retrieveAllMoodGivenUser("xhou1");
        //test.retrieveMostRecentMoodEventOfFriends("xhou1");

    }

    private void verifyNewParticipantCredential() {
        String username = mUsername.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();
        if (username == null || username.trim().length() == 0) {
            mUsername.setError("Username can not be empty");
            return;
        }
        mUsername.setError(null);
        if (password == null || password.trim().length() == 0) {
            mPassword.setError("Password can not be empty");
            return;
        }
        username = username.trim();
        password = password.trim();
        database.signupNewParticipant(username, password, this);

    }

    private void verifyExistingParticipantCredential() {
        String username = mUsername.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();
        if (username == null || username.trim().length() == 0) {
            mUsername.setError("The Username and Password you entered do not match");
            return;
        }
        mUsername.setError(null);
        if (password == null || password.trim().length() == 0) {
            mPassword.setError("The Username and Password you entered do not match");
            return;
        }
        username = username.trim();
        password = password.trim();
        database.authenticExistingParticipant(username, password, this);

    }

    private void init() {
        ImageView logo = findViewById(R.id.participant_loginsignup_app_logo);
        /*
         * Image Source:
         <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a>
         * from <a href="https://www.flaticon.com/"title="Flaticon">www.flaticon.com</a></div>
         */
        mUsername = findViewById(R.id.participant_login_signup_username);
        mPassword = findViewById(R.id.participant_login_signup_password);
        Button loginButton = findViewById(R.id.participant_login_signup_login_btn);
        Button signupButton = findViewById(R.id.participant_login_signup_signup_btn);


        logo.setImageResource(R.drawable.logo);
        database = new Database();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyExistingParticipantCredential();

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                verifyNewParticipantCredential();

            }
        });


    }

    @Override
    public void usernameExist() {
        mUsername.setError("Username already exist, please try another Username");
        mUsername.getEditText().setText(null);


    }

    @Override
    public void usernameNotExist() {
        mUsername.setError("Username does not exist, please enter again");
        mUsername.getEditText().setText(null);


    }

    @Override
    public void existingParticipantLoginSuccessfully() {
        // should switch to Home Screen
        mPassword.setError(null);
        mUsername.setError(null);
        Toast.makeText(this, "Participant log in Successfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void existingParticipantPasswordNotMatch() {
        mPassword.setError("Password is not valid, please enter again");
        mPassword.getEditText().setText(null);

    }

    @Override
    public void existingParticipantLoginError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void newParticipantSignupError(String message) {
        // should switch to Home Screen
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void newParticipantSignupSuccessfully() {

        mPassword.setError(null);
        mUsername.setError(null);
        // should switch to Home Screen
        Toast.makeText(this, "Participant Sign up Successfully", Toast.LENGTH_SHORT).show();


    }
}
