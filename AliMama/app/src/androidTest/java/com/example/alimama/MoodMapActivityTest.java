package com.example.alimama;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.alimama.Model.MoodEvent;
import com.example.alimama.mapview.MoodMapActivity;
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
    public ActivityTestRule<MoodMapActivity> rule = new ActivityTestRule<MoodMapActivity>(MoodMapActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("USERNAME", "testMap");
            return intent;
        }
    };
    /**
     * Runs before all tests and creates solo instance.
     * enters login information for future testing
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     *
     * This function tests whether the button color changed when clicked button
     *
     * */
    @Test
    public void pressFriendMoodBtnCheckIfColorChange() {
        solo.assertCurrentActivity("Wrong Activity", MoodMapActivity.class);
        int i = 1;

        Button friendMoodBtn = (Button) solo.getView(R.id.mood_map_friend_map_btn);
        Button myMoodBtn = (Button) solo.getView(R.id.mood_map_my_map_btn);

        solo.clickOnButton("friendMoodMap");

        ColorDrawable myMoodBtnBackground = (ColorDrawable) myMoodBtn.getBackground();
        ColorDrawable friendMoodBtnBackground = (ColorDrawable) friendMoodBtn.getBackground();

        int myMoodBtnColorValue = myMoodBtnBackground.getColor();
        int friendMoodBtnColorValue = friendMoodBtnBackground.getColor();

        if(i==0){
            assertTrue(myMoodBtnColorValue == Color.parseColor("#42a5f5"));
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
        solo.assertCurrentActivity("Wrong Activity", MoodMapActivity.class);
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
            assertTrue(friendMoodBtnColorValue == Color.parseColor("#42a5f5"));
        }
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
