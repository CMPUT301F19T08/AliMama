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

    private void startMainMenuScreen() {
        Intent goToHomeScreen = new Intent(ParticipantLoginSignupActivity.this, Mainmenu.class);
        goToHomeScreen.putExtra("USERNAME", mUsername.getEditText().getText().toString().trim());
        startActivity(goToHomeScreen);

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
        startMainMenuScreen();


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

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void newParticipantSignupSuccessfully() {

        mPassword.setError(null);
        mUsername.setError(null);
        startMainMenuScreen();


    }
}
