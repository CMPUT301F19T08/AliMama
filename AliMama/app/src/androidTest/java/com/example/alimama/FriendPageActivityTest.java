package com.example.alimama;

import android.app.Activity;
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
    private Solo friendpage;
    private Solo mainmenu;
    private Solo login;

    @Rule
    public ActivityTestRule<FriendPageActivity> rule = new ActivityTestRule<>(FriendPageActivity.class,true,true);

    @Rule
    public ActivityTestRule<ParticipantLoginSignupActivity> rule2 = new ActivityTestRule<>(ParticipantLoginSignupActivity.class,true,true);

    @Rule
    public ActivityTestRule<Mainmenu> rule3 = new ActivityTestRule<>(Mainmenu.class,true,true);

    @Before
    public void setUp() throws Exception{
        friendpage = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        login = new Solo(InstrumentationRegistry.getInstrumentation(),rule2.getActivity());
        mainmenu = new Solo(InstrumentationRegistry.getInstrumentation(),rule3.getActivity());
    }

   /* @Test
    public void start() throws Exception{
        Activity friendPageActivity = rule.getActivity();
        Activity loginActivity = rule2.getActivity();
        Activity mainmenuActivity = rule3.getActivity();
    }*/

    public void loginWithUsername(){
        login.assertCurrentActivity("Wrong Activity",ParticipantLoginSignupActivity.class);
        TextInputLayout usernameTIL = (TextInputLayout)login.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();
        TextInputLayout  passwordTIL = (TextInputLayout)login.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        login.enterText(usernameET, "xhou1");
        friendpage.sleep(2000);
        login.enterText(passwordET,"a380");
        login.clickOnView(login.getView(R.id.participant_login_signup_login_btn));
    }

    public void menupageAccess(){
        TestCase.assertTrue(mainmenu.waitForActivity(Mainmenu.class, 5000));
        // verify that ShowActivity successfully started
        mainmenu.assertCurrentActivity("Not MainMenu Activity", Mainmenu.class);

        mainmenu.clickOnView(mainmenu.getView((R.id.main_menu_add_view_friend_button)));

    }
    @Test
    public void FriendRequestSend(){

        /*Sign in
        * with username = xhou1,password = a380
        *signup.validCredentialExistingParticipantLogIn();*/
        /*press add friend
*/

        loginWithUsername();
        menupageAccess();


        friendpage.assertCurrentActivity("Wrong Activity", FriendPageActivity.class);

        friendpage.clickOnText("Friend Page");
        friendpage.sleep(500);
//        assertTrue(friendpage.searchButton("Add"));
//        friendpage.sleep(2000);
//        friendpage.clickOnButton("Add");
//        friendpage.sleep(2000);
//        friendpage.clickOnText("Contact Page");
        friendpage.clickOnText("Request Page");
//        assertTrue(friendpage.searchText("xhou1"));
//
//
//
//        friendpage.sleep(2000);
//        assertFalse(friendpage.searchButton("Accept"));
//        assertTrue(friendpage.waitForActivity(FriendPageActivity.class, 5000));





    }


    @After
    public void tearDown() throws Exception{
        friendpage.finishOpenedActivities();
    }
}
