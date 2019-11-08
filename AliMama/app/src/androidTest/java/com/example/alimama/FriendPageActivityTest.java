package com.example.alimama;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
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
    private Solo anotheruserFriendPage;

    @Rule
    public ActivityTestRule<FriendPageActivity> rule = new ActivityTestRule<FriendPageActivity>(FriendPageActivity.class,true,true){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("USERNAME","sky1");
            return intent;
        }
    };

   /* @Rule
    public ActivityTestRule<FriendPageActivity> rule4 = new ActivityTestRule<FriendPageActivity>(FriendPageActivity.class,true,true){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("USERNAME","xhou1");
            return intent;
        }
    };*/

    @Rule
    public ActivityTestRule<ParticipantLoginSignupActivity> rule2 =
            new ActivityTestRule<ParticipantLoginSignupActivity>(ParticipantLoginSignupActivity.class,true,true){

        @Override
        protected Intent getActivityIntent() {
           Intent intent = new Intent();
           intent.putExtra("USERNAME","sky1");
            return intent;
        }
    };

    @Rule
    public ActivityTestRule<Mainmenu> rule3 = new ActivityTestRule<Mainmenu>(Mainmenu.class,true,true){
        @Override
    protected Intent getActivityIntent() {
        Intent intent = new Intent();
        intent.putExtra("USERNAME","sky1");
        return intent;
    }};

    @Before
    public void setUp() throws Exception{
        friendpage = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        friendpage.assertCurrentActivity("Wrong Activity", FriendPageActivity.class);
//        anotheruserFriendPage = new Solo(InstrumentationRegistry.getInstrumentation(),rule4.getActivity());
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
        login.enterText(usernameET, "sky1");
        friendpage.sleep(2000);
        login.enterText(passwordET,"sky");
        login.clickOnView(login.getView(R.id.participant_login_signup_login_btn));
    }

    public void loginAnotherUser(){
        login.assertCurrentActivity("Wrong Activity",ParticipantLoginSignupActivity.class);
        TextInputLayout usernameTIL = (TextInputLayout)login.getView(R.id.participant_login_signup_username);
        EditText usernameET = usernameTIL.getEditText();
        TextInputLayout  passwordTIL = (TextInputLayout)login.getView(R.id.participant_login_signup_password);
        EditText passwordET = passwordTIL.getEditText();
        login.enterText(usernameET, "sky1");
        friendpage.sleep(2000);
        login.enterText(passwordET,"sky");
        login.clickOnView(login.getView(R.id.participant_login_signup_login_btn));
    }

    public void menupageAccess(){
        TestCase.assertTrue(mainmenu.waitForActivity(Mainmenu.class, 5000));
        // verify that ShowActivity successfully started
        mainmenu.assertCurrentActivity("Not MainMenu Activity", Mainmenu.class);

        mainmenu.clickOnView(mainmenu.getView((R.id.main_menu_add_view_friend_button)));

    }
    public void confirmRequestAccepted(){
        friendpage.clickOnText("Request Page");
        friendpage.sleep(500);
        friendpage.clickOnText("Accept");
        friendpage.clickOnText("Contact Page");
        assertTrue(friendpage.searchText("xhou3"));
    }
    public void addFriend(){
        ViewGroup tabLayout = (ViewGroup)friendpage.getView(R.id.tabs);
        View friendpagetab = tabLayout.getChildAt(1);
        View requestpagetab = tabLayout.getChildAt(2);
        friendpage.clickOnText("Friend Page");

        friendpage.waitForActivity(FriendPageActivity.class,5000);
        System.out.println("clicked yooooooooooooooooooooooooooooooooooooooooooooooooo");


        RecyclerView recyclerView = (RecyclerView) friendpage.getView(R.id.my_recycler_view,1);
        assertTrue(friendpage.searchText("xhou1"));
        View view = recyclerView.getChildAt(0);
        Button button = (Button)view.findViewById(R.id.friend_add);
        friendpage.sleep(5000);
        friendpage.clickOnView(button);


    }
    @Test
    public void FriendRequestSend(){

        /*Sign in
        * with username = xhou1,password = a380
        *signup.validCredentialExistingParticipantLogIn();*/
        /*press add friend
*/

       /* loginWithUsername();
        menupageAccess();*/

        friendpage.assertCurrentActivity("Wrong Activity", FriendPageActivity.class);
        addFriend();
//        anotheruserFriendPage.assertCurrentActivity("Wrong Activity",FriendPageActivity.class);

//        confirmRequestAccepted();




    }


    @After
    public void tearDown() throws Exception{
        friendpage.finishOpenedActivities();
    }
}
