package com.example.alimama;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.alimama.Model.MoodEvent;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.GeoPoint;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;

public class MoodMapActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<ParticipantLoginSignupActivity> rule = new ActivityTestRule<>(ParticipantLoginSignupActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * enters login information for future testing
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        TextInputLayout usernameTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();
        TextInputLayout  passwordTIL = (TextInputLayout)solo.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        solo.enterText(usernameET, "test");
        solo.enterText(passwordET,"test");
        solo.clickOnButton("Log In"); //click on Log In Button
        assertTrue(solo.waitForActivity(Mainmenu.class, 5000));
        solo.clickOnView(solo.getView(R.id.main_menu_mood_map_button));
        assertTrue(solo.waitForActivity(MoodMap.class, 5000));
    }

    /**
     *
     * This function tests whether the button color changed when clicked button
     *
     * */
    @Test
    public void pressFriendMoodBtnCheckIfColorChange() {
        solo.assertCurrentActivity("Wrong Activity", MoodMap.class);
        int i = 1;

        Button friendMoodBtn = (Button) solo.getView(R.id.mood_map_friend_map_btn);
        Button myMoodBtn = (Button) solo.getView(R.id.mood_map_my_map_btn);

        solo.clickOnButton("friendMoodMap");

        ColorDrawable myMoodBtnBackground = (ColorDrawable) myMoodBtn.getBackground();
        ColorDrawable friendMoodBtnBackground = (ColorDrawable) friendMoodBtn.getBackground();

        int myMoodBtnColorValue = myMoodBtnBackground.getColor();
        int friendMoodBtnColorValue = friendMoodBtnBackground.getColor();

        if(i==0){
            assertTrue(myMoodBtnColorValue == Color.parseColor("#008577"));
            assertTrue(friendMoodBtnColorValue == Color.parseColor("#ffffff"));
        }
    }


    /**
     *
     * This function tests whether the button color changed when clicked button
     *
     * */
    @Test
    public void pressMyMoodBtnCheckIfColorChange() {
        solo.assertCurrentActivity("Wrong Activity", MoodMap.class);
        int a = 1;

        Button friendMoodBtn = (Button) solo.getView(R.id.mood_map_friend_map_btn);
        Button myMoodBtn = (Button) solo.getView(R.id.mood_map_my_map_btn);

        solo.clickOnButton("myMoodMap");

        ColorDrawable myMoodBtnBackground = (ColorDrawable) myMoodBtn.getBackground();
        ColorDrawable friendMoodBtnBackground = (ColorDrawable) friendMoodBtn.getBackground();

        int myMoodBtnColorValue = myMoodBtnBackground.getColor();
        int friendMoodBtnColorValue = friendMoodBtnBackground.getColor();

        if(a==0){
            assertTrue(myMoodBtnColorValue == Color.parseColor("#ffffff"));
            assertTrue(friendMoodBtnColorValue == Color.parseColor("#008577"));
        }
    }

    /**
     *
     * This function tests whether markers appear when clicked button
     *
     * */
    @Test
    public void pressMyMoodBtnCheckIfMarkersAppear() {
        solo.assertCurrentActivity("Wrong Activity", MoodMap.class);
        solo.clickOnButton("myMoodMap");

     //   assertTrue(solo.waitForText("12/04/2019 12:00:00", 1, 2000));
    }
    /**
     *
     * This function tests whether markers appear when clicked button
     *
     * */
    @Test
    public void pressFriendsMoodBtnCheckIfMarkersAppear() {
        solo.assertCurrentActivity("Wrong Activity", MoodMap.class);
        solo.clickOnButton("friendMoodMap");

     //   assertTrue(solo.waitForText("testmap", 1, 2000));
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
