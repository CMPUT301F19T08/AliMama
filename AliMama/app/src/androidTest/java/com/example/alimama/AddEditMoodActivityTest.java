package com.example.alimama;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AddEditMoodActivityTest {
    private Solo solo;

    /**
     * This function creates activity which sets USERNAME as "mood"
     */
    @Rule
    public ActivityTestRule<MoodHistory> rule =
            new ActivityTestRule<MoodHistory> (MoodHistory.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("USERNAME", "mood");
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
        solo.clickOnView(solo.getView(R.id.fab));
        solo.assertCurrentActivity("Wrong activity", AddEditMoodActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etEmotionalState), "Add Test");
        solo.clickOnView(solo.getView(R.id.etDate));
        solo.clickOnButton("OK");

        solo.clickOnView(solo.getView(R.id.etTime));
        solo.clickOnButton("OK");

        solo.enterText((EditText) solo.getView(R.id.etDescription), "Test");

        solo.clickOnView(solo.getView(R.id.spEmoticon));
        solo.pressSpinnerItem(0, 1);

        solo.clickOnView(solo.getView(R.id.spSocialSituation));
        solo.pressSpinnerItem(0, 1);

        solo.clickOnView(solo.getView(R.id.checkBoxLocation));

        solo.clickOnButton("Add Mood");
        SystemClock.sleep(10000);

        final RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.rvMoods);
        View view = recyclerView.getChildAt(0);
        solo.clickOnView(view.findViewById(R.id.btnEditMood));
        SystemClock.sleep(10000);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

//    @Test
//    public void onDateSet() {
//    }
//
//    @Test
//    public void onTimeSet() {
//    }
//
//    @Test
//    public void onCreate() {
//    }
//
//    @Test
//    public void onActivityResult() {
//    }
//
//    @Test
//    public void showDatePickerDialog() {
//    }
//
//    @Test
//    public void showTimePickerDialog() {
//    }
//
//    @Test
//    public void onMapReady() {
//    }
//
//    @Test
//    public void onRequestPermissionsResult() {
//    }
//
//    @Test
//    public void onResume() {
//    }
//
//    @Test
//    public void onPause() {
//    }
//
//    @Test
//    public void onDestroy() {
//    }
//
//    @Test
//    public void onLowMemory() {
//    }
//
//    @Test
//    public void onComplete() {
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
}