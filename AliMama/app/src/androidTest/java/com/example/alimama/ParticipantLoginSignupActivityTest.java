package com.example.alimama;

import android.app.Activity;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;
import androidx.test.platform.app.InstrumentationRegistry;

import static junit.framework.TestCase.assertTrue;


/**
 * UI tests for Participant LoginSignup Screen
 * @author Xuechun Hou
 *
 * */

public class ParticipantLoginSignupActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<ParticipantLoginSignupActivity> rule = new ActivityTestRule<>(ParticipantLoginSignupActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();

    }



    /**
     * This function tests whether appropriate error message will be displayed on Login screen when trying to Sign up with empty Username
     * */
    @Test
    public void emptyUsernameSignUp() {
        // Asserts that the current activity is the ParticipantLoginSignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ParticipantLoginSignupActivity.class);

        TextInputLayout  passwordTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        solo.enterText(passwordET,"aPassword");


        solo.clickOnButton("Sign Up"); //click on Log In Button
        assertTrue(solo.waitForText("Username can not be empty", 1, 2000));


    }
    /**
     * This function tests whether appropriate error message will be displayed on Login screen when trying to Sign up with empty Password
     * */
    @Test
    public void emptyPasswordSignUp() {
        // Asserts that the current activity is the ParticipantLoginSignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ParticipantLoginSignupActivity.class);

        TextInputLayout  usernameTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();

        solo.enterText(usernameET, "xhou1");


        solo.clickOnButton("Sign Up"); //click on Log In Button
        assertTrue(solo.waitForText("Password can not be empty", 1, 2000));


    }

    /**
     * This function tests whether appropriate error message will be displayed on Login screen when trying to Log in without entering
     * a valid Password
     * */
    @Test
    public void emptyPasswordLogIn() {
        // Asserts that the current activity is the ParticipantLoginSignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ParticipantLoginSignupActivity.class);

        TextInputLayout  usernameTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();

        solo.enterText(usernameET, "xhou1");


        solo.clickOnButton("Log In"); //click on Log In Button
        assertTrue(solo.waitForText("The Username and Password you entered do not match", 1, 2000));


    }
    /**
     * This function tests whether appropriate error message will be displayed on Login screen when trying to Log in without entering
     * a valid Username
     * */
    @Test
    public void emptyUsernameLogIn() {
        // Asserts that the current activity is the ParticipantLoginSignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ParticipantLoginSignupActivity.class);

        TextInputLayout  passwordTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        solo.enterText(passwordET,"wrongPassword");

        solo.clickOnButton("Log In"); //click on Log In Button
        assertTrue(solo.waitForText("The Username and Password you entered do not match", 1, 2000));


    }

    /**
     * This function tests whether appropriate error message will be displayed on Login screen when trying to sign up with a username
     * that already exist
     * */
    @Test
    public void alreadyExistUsernameSignUp() {
        // Asserts that the current activity is the ParticipantLoginSignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ParticipantLoginSignupActivity.class);
        TextInputLayout  usernameTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();
        TextInputLayout  passwordTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        solo.enterText(usernameET, "xhou1");
        solo.enterText(passwordET,"wrongPassword");

        solo.clickOnButton("Sign Up"); //click on Log In Button
        assertTrue(solo.waitForText("Username already exist, please try another Username", 1, 2000));


    }


    /**
     * This function tests whether appropriate error message will be displayed on Login screen when trying to login with an username
     * that does not already signed up
     * */
    @Test
    public void unknownUsernameLogIn() {
        // Asserts that the current activity is the ParticipantLoginSignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ParticipantLoginSignupActivity.class);
        TextInputLayout  usernameTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();
        TextInputLayout  passwordTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        solo.enterText(usernameET, "unknownUser");
        solo.enterText(passwordET,"wrongPassword");

        solo.clickOnButton("Log In"); //click on Log In Button
        assertTrue(solo.waitForText("Username does not exist, please enter again", 1, 2000));


    }


    /**
     * This function tests whether appropriate error message will be displayed on Login screen when entering wrong password of an existing Participant and
     * press LogIn Button
     *
     * */
    @Test
    public void invalidPasswordExistingParticipantLogIn() {
        // Asserts that the current activity is the ParticipantLoginSignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ParticipantLoginSignupActivity.class);
        TextInputLayout  usernameTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();
        TextInputLayout  passwordTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        solo.enterText(usernameET, "xhou1");
        solo.enterText(passwordET,"wrongpassword");

        solo.clickOnButton("Log In"); //click on Log In Button
        assertTrue(solo.waitForText("Password is not valid, please enter again", 1, 2000));


    }

    /**
     * This function tests whether will be redirected to MainMenu screen when entering correct credential of an existing Participant and
     * press LogIn Button
     *
     * */
    @Test
    public void validCredentialExistingParticipantLogIn() {
        // Asserts that the current activity is the ParticipantLoginSignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ParticipantLoginSignupActivity.class);
        TextInputLayout  usernameTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();
        TextInputLayout  passwordTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        solo.enterText(usernameET, "xhou1");
        solo.enterText(passwordET,"a380");


        solo.clickOnButton("Log In"); //click on Log In Button
        assertTrue(solo.waitForActivity(Mainmenu.class, 5000));
        // verify that ShowActivity successfully started
        solo.assertCurrentActivity("Not MainMenu Activity", Mainmenu.class);


    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
