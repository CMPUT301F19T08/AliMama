package com.example.alimama;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.alimama.addEditMood.AddEditMoodActivity;
import com.example.alimama.addEditMood.DatePickerFragment;
import com.example.alimama.moodHistory.MoodHistory;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertTrue;


public class AddEditMoodTest {

    private Solo solo;

    /**
     * This function creates activity which sets USERNAME as "test"
     */
    @Rule
    public ActivityTestRule<AddEditMoodActivity> rule = new ActivityTestRule<AddEditMoodActivity>(AddEditMoodActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("USERNAME", "test");
                    return intent;
                }
            };

    /**
     * Runs before all tests and creates solo instance.
     */
    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }


    /**
     * Check if selected emoticon is correct using the emotional state spinner
     */
    @Test
    public void checkEmoticonSpinner(){
        solo.assertCurrentActivity("Wrong Activity", AddEditMoodActivity.class);

        //select the first emotion on the emotional spinner - happy emotion)
        solo.pressSpinnerItem(0,0);
        assertTrue(solo.searchText("happy", true));


        //select the third emotion on the emotional spinner - heart emotion)
        solo.pressSpinnerItem(0,2);
        assertTrue(solo.searchText("feeling loved", true));

        //select the 2nd item in the emotional state spinner
        solo.pressSpinnerItem(0,-1);
        assertTrue(solo.searchText("LOL", true));
    }

    /**
     * Check if selected social situation is correct using the social state spinner
     */
    @Test
    public void checkSocialState(){
        solo.assertCurrentActivity("Wrong Activity", AddEditMoodActivity.class);

        //select the second social state emotion on the social state spinner - with one other person)
        solo.pressSpinnerItem(1,1);
        assertTrue(solo.isSpinnerTextSelected(1,"with one other person"));

        //select the fourth social state emotion on the social state spinner - with a crowd)
        solo.pressSpinnerItem(1,2);
        assertTrue(solo.isSpinnerTextSelected(1,"with a crowd"));

        //select the third social state emotion on the social state spinner - with teo to several ppl)
        solo.pressSpinnerItem(1,-1);
        assertTrue(solo.isSpinnerTextSelected(1,"with two to several ppl"));

    }

    /**
     * Check if description is entered
     */
    @Test
    public void checkDescText(){
        solo.clickOnText("description");
        solo.enterText(0,"had a good day");
        assertTrue(solo.searchText("had a good day", true));

        //tests the 20 character limit
        solo.clickOnText("description");
        solo.enterText(0,"had a very good day!!!");
        assertTrue(solo.searchText("had a very good day!", true));
    }

    /**
     * Check if the checkbox to ask user to record location is checked
     */
    @Test
    public void checkCheckBox(){
        solo.clickOnView(solo.getView(R.id.checkBoxLocation));
        assertTrue(solo.isCheckBoxChecked(0));
    }

    /**
     * Tests to see if correct date is added
     */
    @Test
    public void checkDatePicker(){
        // see if november 10 2019 is added - remember to +1 to the month
        solo.clickOnText("date required");
        solo.setDatePicker(0, 2019,10,10);
        solo.clickOnButton("OK");
        assertTrue(solo.searchText("2019-11-10", true));
    }

    /**
     * Tests to see if correct time is added
     */
    @Test
    public void checkTimePicker(){
        solo.clickOnText("time required");
        solo.setTimePicker(0, 12,23);
        solo.clickOnButton("OK");
        assertTrue(solo.searchText("12:23", true));
    }


//    @Test
//    public void checkAddMood(){
//        //add date
//        solo.clickOnText("date required");
//        solo.setDatePicker(0, 2019,9,15);
//        solo.clickOnButton("OK");
//        //assertTrue(solo.searchText("2019-10-15", true));
//
//        //add time
//        solo.clickOnText("time required");
//        solo.setTimePicker(0, 1,48);
//        solo.clickOnButton("OK");
//        //assertTrue(solo.searchText("1:48", true));
//
//        //add description
//        solo.clickOnText("description");
//        solo.enterText(2,"amazing day");
//        //assertTrue(solo.searchText("amazing day", true));
//
//        //select the third emotion on the emotional spinner - heart emotion)
//        solo.pressSpinnerItem(0,2);
//        //assertTrue(solo.searchText("heart", true));
//
//        //select the second social state emotion on the social state spinner - with one other person)
//        solo.pressSpinnerItem(1,1);
//        //assertTrue(solo.isSpinnerTextSelected(1,"with one other person"));
//
//        //check the checkbox
//        solo.clickOnView(solo.getView(R.id.checkBoxLocation));
//
//        //add the mood to the current users mood history
//        solo.clickOnButton("Add Mood");
//        SystemClock.sleep(10000);
//        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
//        //assertTrue(solo.searchText("heart", true));
//
//    }

    /**
     * Closes the activity after each test
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}
