package com.example.alimama;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


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
     * Check the names and the numbers of my Mood history using assertTrue and
     * Check the names and the numbers of friends' Mood history using assertTrue and
     * Check whether the app switch to Add/Edit Mood Activity after the floating button is pressed
     */
    @Test
    public void checkList() {
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
        assertTrue(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou2", true));
        assertFalse(solo.searchText("xhou3", true));
        assertFalse(solo.searchText("xhou4", true));
        assertEquals(9, ((RecyclerView) solo.getCurrentActivity().findViewById(R.id.rvMoods)).getAdapter().getItemCount());

        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.btnFriendsHistory));
        assertTrue(solo.searchText("xhou2", true));
        assertFalse(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou3", true));
        assertFalse(solo.searchText("xhou4", true));
        assertEquals(1, ((RecyclerView) solo.getCurrentActivity().findViewById(R.id.rvMoods)).getAdapter().getItemCount());

        solo.pressSpinnerItem(0, 1);
        assertFalse(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou2", true));
        assertEquals(0, ((RecyclerView) solo.getCurrentActivity().findViewById(R.id.rvMoods)).getAdapter().getItemCount());

        solo.pressSpinnerItem(0, 3);
        assertTrue(solo.searchText("xhou2", true));
        assertFalse(solo.searchText("xhou4", true));
        assertEquals(1, ((RecyclerView) solo.getCurrentActivity().findViewById(R.id.rvMoods)).getAdapter().getItemCount());

        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.btnMyHistory));
        solo.pressSpinnerItem(0, -4);
        assertTrue(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou3", true));
        assertEquals(9, ((RecyclerView) solo.getCurrentActivity().findViewById(R.id.rvMoods)).getAdapter().getItemCount());

        solo.pressSpinnerItem(0, 1);
        assertTrue(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou4", true));
        assertEquals(5, ((RecyclerView) solo.getCurrentActivity().findViewById(R.id.rvMoods)).getAdapter().getItemCount());

        solo.pressSpinnerItem(0, 5);
        assertTrue(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou3", true));
        assertEquals(4, ((RecyclerView) solo.getCurrentActivity().findViewById(R.id.rvMoods)).getAdapter().getItemCount());

        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.fab));
        solo.assertCurrentActivity("Wrong activity", AddEditMoodActivity.class);

        solo.goBack();
        solo.pressSpinnerItem(0, -6);
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
        assertTrue(solo.searchText("xhou1", true));
        assertFalse(solo.searchText("xhou4", true));
        assertEquals(9, ((RecyclerView) solo.getCurrentActivity().findViewById(R.id.rvMoods)).getAdapter().getItemCount());
    }

    /**
     * Runs before all tests and creates solo instance.
     */
    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.assertCurrentActivity("Wrong activity", MoodHistory.class);
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
     * Closes the activity after each test
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

//    @Test
//    public void onCreate() {
//    }
//
//    @Test
//    public void onResume() {
//    }
//
//    @Test
//    public void failToUpdateAnExistingMoodEvent() {
//    }
//
//    @Test
//    public void updateAnExistingMoodEventSuccessfully() {
//    }
//
//    @Test
//    public void failToAddANewMoodEvent() {
//    }
//
//    @Test
//    public void addANewMoodEventSuccessfully() {
//    }
//
//    @Test
//    public void retrieveAllMoodEventOfAParticipantSuccessfully() {
//    }
//
//    @Test
//    public void failToRetrieveAllMoodEventOfAParticipant() {
//    }
//
//    @Test
//    public void deleteAMoodEventOfAParticipantSuccessfully() {
//    }
//
//    @Test
//    public void failToDeleteAMoodEventOfAParticipant() {
//    }
//
//    @Test
//    public void failRetrieveMostRecentMoodEventOfFriendsOfAParticipant() {
//    }
//
//    @Test
//    public void retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully() {
//    }
//
//    @Test
//    public void failRegisterMoodEventRealTimeListener() {
//    }
//
//    @Test
//    public void onEditClick() {
//    }
//
//    @Test
//    public void onDeleteClick() {
//    }
}