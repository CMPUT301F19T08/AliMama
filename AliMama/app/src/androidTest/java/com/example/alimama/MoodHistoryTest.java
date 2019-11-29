package com.example.alimama;

import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.alimama.Model.MoodEvent;
import com.example.alimama.addEditMood.AddEditMoodActivity;
import com.example.alimama.moodHistory.MoodHistory;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Test class for MoodHistoryActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class MoodHistoryTest {
    private Solo solo;

    /**
     * This function creates activity which sets USERNAME as "test"
     */
    @Rule
    public ActivityTestRule<MoodHistory> rule =
            new ActivityTestRule<MoodHistory>(MoodHistory.class) {
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
     * Gets the Activity
     */
    @Test
    public void start() {
        rule.getActivity();
        solo.assertCurrentActivity("Wrong activity", MoodHistory.class);
    }

    /**
     *
     * This function tests whether we switched to the friends Mood history activity tab when clicked
     *
     * */
    @Test
    public void checkIfSwitchedToFriendsHistoryTab() {
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);

        solo.clickOnView(solo.getView(R.id.btnFriendsHistory));

        TextView text = (TextView) solo.getCurrentActivity().findViewById(R.id.btnMyHistory);
        assertEquals(Color.parseColor("#42a5f5") , text.getCurrentTextColor() );
    }


    /**
     *
     * This function tests whether we switched to the My Mood history activity tab when clicked
     *
     * */
    @Test
    public void checkIfSwitchedToMyHistoryTab() {
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);

        solo.clickOnView(solo.getView(R.id.btnMyHistory));

        TextView text = (TextView) solo.getCurrentActivity().findViewById(R.id.btnMyHistory);
        assertEquals(Color.parseColor("#42a5f5") , text.getCurrentTextColor() );

    }


    /**
     * Check if add mood button works
     */
    @Test
    public void checkAddButton(){
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.fab));
        solo.assertCurrentActivity("Wrong activity", AddEditMoodActivity.class);
    }

    /**
     * Tests if when edit button on mood is clicked, will go to the edit mood screen
     */
    @Test
    public void onEditClick() {

        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.fab));
        solo.assertCurrentActivity("Wrong activity", AddEditMoodActivity.class);
        //add date
        solo.clickOnText("date required");
        solo.setDatePicker(0, 2019,9,15);
        solo.clickOnButton("OK");
        //assertTrue(solo.searchText("2019-10-15", true));

        //add time
        solo.clickOnText("time required");
        solo.setTimePicker(0, 1,48);
        solo.clickOnButton("OK");
        //assertTrue(solo.searchText("1:48", true));

        //add description
        solo.clickOnText("description");
        solo.enterText(2,"amazing day");
        //assertTrue(solo.searchText("amazing day", true));

        //select the third emotion on the emotional spinner - heart emotion)
        solo.pressSpinnerItem(0,2);
        //assertTrue(solo.searchText("heart", true));

        //select the second social state emotion on the social state spinner - with one other person)
        solo.pressSpinnerItem(1,1);
        //assertTrue(solo.isSpinnerTextSelected(1,"with one other person"));

        //check the checkbox
        solo.clickOnView(solo.getView(R.id.checkBoxLocation));

        //add the mood to the current users mood history
        solo.clickOnButton("Add Mood");
        SystemClock.sleep(10000);
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
        assertTrue(solo.searchText("test", true));

        solo.clickInRecyclerView(0,0,R.id.btnEditMood);
        assertTrue(solo.searchText("Edit Mood", true));

        //deletes test mood when test is done
        solo.clickOnButton("Edit Mood");
        solo.clickInRecyclerView(0,0,R.id.btnDeleteMood);
        assertFalse(solo.searchText("test"));
    }

    @Test
    public void onDeleteClick() {

        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.fab));
        solo.assertCurrentActivity("Wrong activity", AddEditMoodActivity.class);
        //add date
        solo.clickOnText("date required");
        solo.setDatePicker(0, 2019,9,15);
        solo.clickOnButton("OK");
        //assertTrue(solo.searchText("2019-10-15", true));

        //add time
        solo.clickOnText("time required");
        solo.setTimePicker(0, 1,48);
        solo.clickOnButton("OK");
        //assertTrue(solo.searchText("1:48", true));

        //add description
        solo.clickOnText("description");
        solo.enterText(2,"amazing day");
        //assertTrue(solo.searchText("amazing day", true));

        //select the third emotion on the emotional spinner - heart emotion)
        solo.pressSpinnerItem(0,2);
        //assertTrue(solo.searchText("heart", true));

        //select the second social state emotion on the social state spinner - with one other person)
        solo.pressSpinnerItem(1,2);
        //assertTrue(solo.isSpinnerTextSelected(1,"with one other person"));

        //check the checkbox
        solo.clickOnView(solo.getView(R.id.checkBoxLocation));

        //add the mood to the current users mood history
        solo.clickOnButton("Add Mood");
        SystemClock.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
        assertTrue(solo.searchText("test", true));

        //deletes test mood
        solo.clickInRecyclerView(0,0,R.id.btnDeleteMood);
        SystemClock.sleep(10000);
        assertFalse(solo.searchText("test"));
    }

    /**
     * Check if selected emotion on spinner filters the mood history by the selected mood
     */
    @Test
    public void checkMoodFilter(){
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.fab));
        solo.assertCurrentActivity("Wrong activity", AddEditMoodActivity.class);
        //add date
        solo.clickOnText("date required");
        solo.setDatePicker(0, 2019,9,15);
        solo.clickOnButton("OK");
        //assertTrue(solo.searchText("2019-10-15", true));

        //add time
        solo.clickOnText("time required");
        solo.setTimePicker(0, 1,48);
        solo.clickOnButton("OK");
        //assertTrue(solo.searchText("1:48", true));

        //add description
        solo.clickOnText("description");
        solo.enterText(2,"amazing day");
        //assertTrue(solo.searchText("amazing day", true));

        //select the third emotion on the emotional spinner - heart emotion)
        solo.pressSpinnerItem(0,2);
        //assertTrue(solo.searchText("heart", true));

        //select the second social state emotion on the social state spinner - with one other person)
        solo.pressSpinnerItem(1,2);
        //assertTrue(solo.isSpinnerTextSelected(1,"with one other person"));

        //check the checkbox
        solo.clickOnView(solo.getView(R.id.checkBoxLocation));

        //add the mood to the current users mood history
        solo.clickOnButton("Add Mood");

        SystemClock.sleep(10000);
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
        assertTrue(solo.searchText("test", true));

        //select a filter with no moods
        solo.pressSpinnerItem(0,1);
        assertFalse(solo.searchText("test"));
        
        //select a filter with a mood
        solo.pressSpinnerItem(0,2);
        assertTrue(solo.searchText("test", true));

        //deletes test mood
        solo.clickInRecyclerView(0,0,R.id.btnDeleteMood);
        SystemClock.sleep(10000);
        assertFalse(solo.searchText("test", true));


    }

    /**
     * Closes the activity after each test
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

}