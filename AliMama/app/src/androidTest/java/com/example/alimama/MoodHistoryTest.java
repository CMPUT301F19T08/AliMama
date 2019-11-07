package com.example.alimama;

import android.app.Activity;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Test class for MoodHistoryA. All the UI tests are written here. Robotium test framework is
 used
 */
public class MoodHistoryTest {
    private Solo solo;

    /**
     * Add a city to the listview and check the city name using assertTrue
     * Clear all the cities from the listview and check again with assertFalse
     */
    @Test
    public void checkList() {
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.btnFriendsHistory));
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.btnMyHistory));
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.fab));
        solo.assertCurrentActivity("Wrong activity", AddEditMoodActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MoodHistory.class);
    }

    @Rule
    public ActivityTestRule<MoodHistory> rule =
            new ActivityTestRule<>(MoodHistory.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Gets the Activity
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Closes the activity after each test
     */
    @After
    public void tearDown() throws Exception{
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