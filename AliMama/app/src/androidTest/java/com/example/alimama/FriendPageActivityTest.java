package com.example.alimama;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import com.example.alimama.Controller.TabPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.robotium.solo.Solo;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * UI testing for FriendActivity Screen
 * @author Zi Xuan Zhang
 * */

public class FriendPageActivityTest {
    private Solo solo;



    @Rule
    public ActivityTestRule<FriendPageActivity> rule = new ActivityTestRule<FriendPageActivity>(FriendPageActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("USERNAME", "test");
            return intent;
        }
    };

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }

   /**
    *
    * if logged in as test should expect xhou1 as test's existing contacts
    * */
    @Test
    public void checkExistingFriendsOfAParticipant(){

        solo.assertCurrentActivity("Wrong Activity", FriendPageActivity.class);
        solo.waitForText("xhou1", 1, 2000);

    }

    /**
     *
     * if logged in as test should expect pending friend request from xhou2 shows on request page
     * */
    @Test
    public void checkExistingPendingFriendRequestOfAParticipant(){

        solo.assertCurrentActivity("Wrong Activity", FriendPageActivity.class);
        TabLayout tabs = (TabLayout) solo.getView(R.id.tabs);
        tabs.getTabAt(2).select();


        solo.waitForText("xhou2", 1, 2000);

    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
