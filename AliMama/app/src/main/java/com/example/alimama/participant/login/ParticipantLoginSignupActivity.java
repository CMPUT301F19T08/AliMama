package com.example.alimama.participant.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alimama.homescreen.HomeScreenActivity;
import com.example.alimama.Model.UserProfile;
import com.example.alimama.R;
import com.google.android.material.textfield.TextInputLayout;


/**
 * This is the first screen of the the whole application which allows new Participants to sign up or existing Participants to log in.
 * this class implements ParticipantLoginContract.ParticipantLoginView interface to conform Model-View-Presenter design pattern
 * No outstanding issues identified
 * @author Xuechun Hou
 */
public class ParticipantLoginSignupActivity extends AppCompatActivity implements  ParticipantLoginContract.ParticipantLoginView {


    private TextInputLayout mUsername;
    private TextInputLayout mPassword;
    private ParticipantLoginPresenter mParticipantLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_login_signup);

        init();
    }
    /**
     * this function retrieves the reference to the Views on the screen, sets the application logo, as well
     * as register button onClickListener
     * */
    private void init() {

        this.mUsername = findViewById(R.id.participant_login_signup_username);
        this.mPassword = findViewById(R.id.participant_login_signup_password);
        this.mParticipantLoginPresenter = new ParticipantLoginPresenter(this);
        Button loginButton = findViewById(R.id.participant_login_signup_login_btn);
        Button signupButton = findViewById(R.id.participant_login_signup_signup_btn);
        ImageView logo = findViewById(R.id.participant_loginsignup_app_logo);
        /*
         * Image Source:
         <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a>
         * from <a href="https://www.flaticon.com/"title="Flaticon">www.flaticon.com</a></div>
         */
        logo.setImageResource(R.drawable.logo);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mUsername.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                mParticipantLoginPresenter.verifyExistingParticipantCredential(username, password);

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username = mUsername.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                mParticipantLoginPresenter.verifyNewParticipantCredential(username, password);


            }
        });


    }

    /**
     * this method displays username empty error onto the Participant login screen
     * */
    @Override
    public void displayUsernameEmptyError() {
        mUsername.setError("Username can not be empty");

    }

    /**
     * this method displays password empty error onto the Participant login screen
     * */
    @Override
    public void displayPasswordEmptyError() {
        mPassword.setError("Password can not be empty");
    }

    /**
     * this method displays username password don't match error onto the Participant login screen
     * */
    @Override
    public void displayUsernamePasswordNotMatchError() {
        mUsername.setError("The Username and Password you entered do not match");
        mUsername.getEditText().setText(null);
        mPassword.getEditText().setText(null);

    }

    /**
     * this method displays username already exist error onto the Participant login screen
     * */
    @Override
    public void displayUsernameAlreadyExistError() {
        mUsername.setError("Username already exist, please try another Username");
        mUsername.getEditText().setText(null);
        mPassword.getEditText().setText(null);

    }

    /**
     * this method displays username doesn't exist error onto the Participant login screen
     * */
    @Override
    public void displayUsernameDoNotExistError() {
        mUsername.setError("Username does not exist, please enter again");
        mUsername.getEditText().setText(null);
        mPassword.getEditText().setText(null);

    }

    /**
     * this method displays Toast onto the Participant login screen to indicate that existing participant log in successfully
     * */
    @Override
    public void displayExistingParticipantLoginSuccessToast() {
        Toast.makeText(this, "log in successfully", Toast.LENGTH_SHORT).show();

    }

    /**
     * this method displays Toast onto the Participant login screen to indicate that new participant sign up successfully
     * */
    @Override
    public void displayNewParticipantSignupSuccessToast() {
        Toast.makeText(this, "sign up successfully", Toast.LENGTH_SHORT).show();

    }

    /**
     * this method displays Toast onto the Participant login screen to indicate that errors occur during new participant sign up
     * */
    @Override
    public void displayNewParticipantSignupDatabaseErrorToast(String message) {
        mUsername.getEditText().setText(null);
        mPassword.getEditText().setText(null);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    /**
     * this method displays Toast onto the Participant login screen to indicate that errors occur during existing participant log in
     * */
    @Override
    public void displayExistingParticipantLoginDatabaseErrorToast(String message) {
        mUsername.getEditText().setText(null);
        mPassword.getEditText().setText(null);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    /**
     * this method clears error displayed under username input field
     * */
    @Override
    public void clearUsernameError() {

        mUsername.setError(null);


    }

    /**
     * this method clears error displayed under password input field
     * */
    @Override
    public void clearPasswordError() {
        mPassword.setError(null);

    }

    /**
     * this method starts main menu screen and passes along information of current logged-in participant
     * */
    @Override
    public void startMainScreen(UserProfile currentLoggedInParticipant) {
        Intent goToHomeScreen = new Intent(ParticipantLoginSignupActivity.this, HomeScreenActivity.class);
        goToHomeScreen.putExtra("USERNAME", currentLoggedInParticipant.getUsername());
        startActivity(goToHomeScreen);

    }
}