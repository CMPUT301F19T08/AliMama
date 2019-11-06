package com.example.alimama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;



/**
 * This is the first screen of the the whole application which allows new Participants to sign up or existing Participants
 * to log in.
 * @author Xuechun Hou
 */
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

    /**
     * this function verify the credential of the new Participant.

     * */

    private void verifyNewParticipantCredential() {
        String username = mUsername.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();
        if (username.trim().length() == 0) {
            mUsername.setError("Username can not be empty");
            return;
        }
        mUsername.setError(null);
        if (password.trim().length() == 0) {
            mPassword.setError("Password can not be empty");
            return;
        }
        username = username.trim();
        password = password.trim();
        database.signupNewParticipant(username, password, this);

    }


    /**
     * this function verify the credential of the existing Participant.

     * */
    private void verifyExistingParticipantCredential() {
        String username = mUsername.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();
        if (username.trim().length() == 0) {
            mUsername.setError("The Username and Password you entered do not match");
            return;
        }
        mUsername.setError(null);
        if (password.trim().length() == 0) {
            mPassword.setError("The Username and Password you entered do not match");
            return;
        }
        username = username.trim();
        password = password.trim();
        database.authenticExistingParticipant(username, password, this);

    }

    /**
     * this function retrieves the reference to the Views on the screen, sets the application logo, as well
     * as register button onClickListener
     * */
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

    /**
     * This function sends intent to MainMenu Screen with username of current logged-in Participant sent along the intent
     *
     * */
    private void startMainMenuScreen() {
        Intent goToHomeScreen = new Intent(ParticipantLoginSignupActivity.this, Mainmenu.class);
        goToHomeScreen.putExtra("USERNAME", mUsername.getEditText().getText().toString().trim());
        startActivity(goToHomeScreen);

    }

    /**
     * This function is part of implementation of CredentialValidationDelegate Interface, which displays error
     * on screen if username exists in database when new Participant sign up
     *
     * */
    @Override
    public void usernameExist() {
        mUsername.setError("Username already exist, please try another Username");
        mUsername.getEditText().setText(null);


    }

    /**
     * This function is part of implementation of CredentialValidationDelegate Interface, which displays error
     * on screen if username does not exist in database when existing Participant logs in
     *
     * */
    @Override
    public void usernameNotExist() {
        mUsername.setError("Username does not exist, please enter again");
        mUsername.getEditText().setText(null);


    }


    /**
     * This function is part of implementation of CredentialValidationDelegate Interface, which clears
     * errors that displayed on screen (if any) when existing Participant logged in successfully
     *
     * */
    @Override
    public void existingParticipantLoginSuccessfully() {
        // should switch to Home Screen
        mPassword.setError(null);
        mUsername.setError(null);
        startMainMenuScreen();


    }



    /**
     * This function is part of implementation of CredentialValidationDelegate Interface, which displays
     * errors when existing Participant enters wrong password.
     *
     * */
    @Override
    public void existingParticipantPasswordNotMatch() {
        mPassword.setError("Password is not valid, please enter again");
        mPassword.getEditText().setText(null);

    }

    /**
     * This function is part of implementation of CredentialValidationDelegate Interface, which displays
     * a Toast when existing Participant failed to log in
     *
     * */

    @Override
    public void existingParticipantLoginError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * This function is part of implementation of CredentialValidationDelegate Interface, which displays
     * a Toast when new Participant failed to  sign up
     *
     * */

    @Override
    public void newParticipantSignupError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


    }


    /**
     * This function is part of implementation of CredentialValidationDelegate Interface, which clears
     * errors that displayed on screen (if any) when new Participant signed in successfully
     *
     * */
    @Override
    public void newParticipantSignupSuccessfully() {

        mPassword.setError(null);
        mUsername.setError(null);
        startMainMenuScreen();


    }
}
