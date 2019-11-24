package com.example.alimama;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.alimama.addEditMood.AddEditMoodActivity;
import com.example.alimama.moodHistory.MoodHistory;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Test class for MoodHistoryActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class MoodHistoryTest {
    private Solo solo;

    /**
     * This function creates activity which sets USERNAME as "xhou1"
     */
    @Rule
    public ActivityTestRule<MoodHistory> rule =
            new ActivityTestRule<MoodHistory>(MoodHistory.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("USERNAME", "xhou1");
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

        assertTrue(solo.searchText("xhou2", true));
        assertFalse(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou3", true));
        assertFalse(solo.searchText("xhou4", true));
    }


    /**
     *
     * This function tests whether we switched to the My Mood history activity tab when clicked
     *
     * */
    @Test
    public void checkIfSwitchedToMyHistoryTab() {
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);

        solo.clickOnView(solo.getView(R.id.btnFriendsHistory));

        assertTrue(solo.searchText("xhou2", true));
        assertFalse(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou3", true));
        assertFalse(solo.searchText("xhou4", true));

        solo.clickOnView(solo.getView(R.id.btnMyHistory));

        assertTrue(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou2", true));
        assertFalse(solo.searchText("xhou3", true));

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
     * Tests if when delete button on mood is clicked, will delete selected mood
     */
    @Test
    public void onDeleteClick() {
        rule.getActivity();
        //solo.assertCurrentActivity("Wrong activity", MoodHistory.class);
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
        final RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.rvMoods);
        View view = recyclerView.getChildAt(0);
        SystemClock.sleep(5000);
        solo.clickOnView(solo.getView(R.id.btnDeleteMood));
        SystemClock.sleep(5000);
    }

    /**
     * Tests if when edit button on mood is clicked, will switch to the Edit mood screen
     */
    @Test
    public void onEditClick() {
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
        final RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.rvMoods);
        View view = recyclerView.getChildAt(0);
        SystemClock.sleep(3000);
        solo.clickOnView(solo.getView(R.id.btnEditMood));
        solo.assertCurrentActivity("Wrong Activity", AddEditMoodActivity.class);
    }


    /**
     * Closes the activity after each test
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

}