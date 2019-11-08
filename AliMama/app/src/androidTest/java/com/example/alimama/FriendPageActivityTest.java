package com.example.alimama;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import android.widget.Button;


import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import com.google.android.material.tabs.TabLayout;
import com.robotium.solo.Solo;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Text;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * UI testing for FriendActivity Screen
 * @author Zi Xuan Zhang
 * */

public class FriendPageActivityTest {
    private Solo friendpage;
    private Solo mainmenu;
    private TextView textView;
    RecyclerView recyclerView;
    CardView cardView;
    String name;

    @Rule
    public ActivityTestRule<FriendPageActivity> rule = new ActivityTestRule<FriendPageActivity>(FriendPageActivity.class,true,true){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("USERNAME","sky1");
            return intent;
        }
    };


    @Rule
    public ActivityTestRule<Mainmenu> rule2 = new ActivityTestRule<Mainmenu>(Mainmenu.class,true,true){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("USERNAME", "test");
            return intent;
        }
    };



    @Before
    public void setUp() throws Exception{
        friendpage = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        friendpage.assertCurrentActivity("Wrong Activity", FriendPageActivity.class);

        mainmenu = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());



    }

    @Test
    public void start() throws Exception{
        Activity friendPageActivity = rule.getActivity();


    }

    public String seekFriend(){

        friendpage.clickOnText("Friends");

        textView = (TextView) friendpage.getView(R.id.friend_name);
        name = textView.getText().toString();
        friendpage.clickOnButton("Add");
        friendpage.goBack();
        mainmenu.assertCurrentActivity("Wrong Activity", Mainmenu.class);


        return name;
    }





    public void seekRequest(){
        mainmenu.assertCurrentActivity("Wrong Activity", Mainmenu.class);
        mainmenu.clickOnView(mainmenu.getView(R.id.main_menu_add_view_friend_button));
        mainmenu.clickOnText("Requests");
        textView = (TextView) mainmenu.getView(R.id.request_name);
        String confirmName = textView.getText().toString();
        assertTrue(mainmenu.searchText("sky1"));
        mainmenu.clickOnButton("Accept");
        mainmenu.clickOnText("Contacts");
        textView = (TextView) mainmenu.getView(R.id.contact_name);
        String sameName = textView.getText().toString();
        assertEquals(sameName,confirmName);

    }
    @Test
    public void FriendRequestSend(){


        seekFriend();
        seekRequest();


    }


    @After
    public void tearDown() throws Exception{
        friendpage.finishOpenedActivities();
    }
}
