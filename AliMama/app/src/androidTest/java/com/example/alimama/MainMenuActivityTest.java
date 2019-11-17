package com.example.alimama;

import com.example.alimama.friendOperation.FriendPageActivity;
import com.example.alimama.homescreen.HomeScreenActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static junit.framework.TestCase.assertTrue;

public class MainMenuActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<HomeScreenActivity> rule = new ActivityTestRule<>(HomeScreenActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }


//    /**
//     *
//     *This function tests whether the application switches to ParticipantLoginSignup Activity when pressing the
//     * Log out Button
//     *
//     * */
//    @Test
//    public void pressLogOutButton() {
//        solo.assertCurrentActivity("Wrong Activity", HomeScreenActivity.class);
//        solo.clickOnView(solo.getView(R.id.main_menu_logout_button));
//        assertTrue(solo.waitForActivity(ParticipantLoginSignupActivity.class, 5000));
//        // verify that ParticipantLoginSignupActivity successfully started
//        solo.assertCurrentActivity("Not ParticipantLoginSignupActivity Activity", ParticipantLoginSignupActivity.class);
//
//
//    }


    /**
     *
     *This function tests whether the application switches to FriendPageActivity Activity when pressing the
     * Add/View Friends Button
     *
     * */
    @Test
    public void pressViewAddFriendsButton() {
        solo.assertCurrentActivity("Wrong Activity", HomeScreenActivity.class);
        solo.clickOnView(solo.getView(R.id.main_menu_add_view_friend_button));
        assertTrue(solo.waitForActivity(FriendPageActivity.class, 5000));
        // verify that FriendPageActivity successfully started
        solo.assertCurrentActivity("Not MoodMap Activity", FriendPageActivity.class);


    }


    /**
     *
     *This function tests whether the application switches to MoodMap Activity when pressing the
     * View Mood History On Map Button
     *
     * */
    @Test
    public void pressViewMoodHistoryOnMapButton() {
        solo.assertCurrentActivity("Wrong Activity", HomeScreenActivity.class);
        solo.clickOnView(solo.getView(R.id.main_menu_mood_map_button));
        assertTrue(solo.waitForActivity(MoodMap.class, 5000));
        // verify that MoodMap successfully started
        solo.assertCurrentActivity("Not MoodMap Activity", MoodMap.class);


    }

    /**
     *
     *This function tests whether the application switches to MoodHistory Activity when pressing the
     * View Mood History Button
     *
     * */
    @Test
    public void pressViewMoodHistoryButton() {
        solo.assertCurrentActivity("Wrong Activity", HomeScreenActivity.class);
        solo.clickOnView(solo.getView(R.id.main_menu_mood_history_button));
        assertTrue(solo.waitForActivity(MoodHistory.class, 5000));
        // verify that MoodHistory successfully started
        solo.assertCurrentActivity("Not View Mood History Activity", MoodHistory.class);


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
