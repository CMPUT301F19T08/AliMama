package com.example.alimama;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
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
    public ActivityTestRule<Mainmenu> rule = new ActivityTestRule<>(Mainmenu.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }


    /**
     *
     *
     *
     * */
    @Test
    public void pressViewMoodHistoryButton() {
        solo.assertCurrentActivity("Wrong Activity", Mainmenu.class);



        solo.clickOnView(solo.getView(R.id.main_menu_mood_history_button));
        assertTrue(solo.waitForActivity(MoodHistory.class, 5000));
        // verify that ShowActivity successfully started
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
