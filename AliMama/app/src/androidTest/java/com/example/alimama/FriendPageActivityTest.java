package com.example.alimama;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import android.widget.Button;


import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;





import com.robotium.solo.Solo;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * UI testing for FriendActivity Screen
 * @author Zi Xuan Zhang
 * */

public class FriendPageActivityTest {
    private Solo friendpage;


    @Rule
    public ActivityTestRule<FriendPageActivity> rule = new ActivityTestRule<FriendPageActivity>(FriendPageActivity.class,true,true){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("USERNAME","sky1");
            return intent;
        }
    };



    @Before
    public void setUp() throws Exception{
        friendpage = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        friendpage.assertCurrentActivity("Wrong Activity", FriendPageActivity.class);


    }

    @Test
    public void start() throws Exception{
        Activity friendPageActivity = rule.getActivity();


    }


    public void tabworking() {
        TestCase.assertTrue(friendpage.waitForActivity(FriendPageActivity.class, 5000));
        // verify that ShowActivity successfully started
        friendpage.assertCurrentActivity("Wrong Activity", FriendPageActivity.class);
        friendpage.clickOnText("Requests");
        friendpage.sleep(500);
        friendpage.clickOnText("Friends");
        friendpage.sleep(500);
        friendpage.clickOnText("Contacts");
        assertTrue(friendpage.searchText("xhou3"));


    }
    public void addFriend(){

        friendpage.clickOnText("Requests");
        friendpage.clickOnText("Friends");
        friendpage.clickOnButton(0);
        friendpage.waitForActivity(FriendPageActivity.class,5000);
        friendpage.sleep(5000);

    }

    public void seekFriend(){
        friendpage.clickOnText("Requests");

        friendpage.clickOnText("Friends");


        RecyclerView recyclerView = (RecyclerView) friendpage.getView(R.id.my_recycler_view);
        View view = recyclerView.getChildAt(0);
        TextView textView = view.findViewById(R.id.contact_name);
        String name  = textView.getText().toString();
        Button button = (Button)view.findViewById(R.id.friend_add);
        friendpage.clickOnView(button);
        assertEquals(name,"xhou1");
        System.out.println(name);



    }
    @Test
    public void FriendRequestSend(){

//        tabworking();
        seekFriend();


    }


    @After
    public void tearDown() throws Exception{
        friendpage.finishOpenedActivities();
    }
}
