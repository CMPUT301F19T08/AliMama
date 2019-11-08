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




    public void seekFriend(){


        friendpage.clickOnText("Friends");



        RecyclerView recyclerView = (RecyclerView) friendpage.getView(R.id.my_recycler_view);
        View view = recyclerView.getChildAt(0);
        TextView textView = view.findViewById(R.id.friend_name);
        String name  = textView.getText().toString();
        Button button = (Button)view.findViewById(R.id.friend_add);
        assertEquals(name,"xhou1");
        friendpage.clickOnView(button);

        System.out.println(name);



    }
    @Test
    public void FriendRequestSend(){


        seekFriend();


    }


    @After
    public void tearDown() throws Exception{
        friendpage.finishOpenedActivities();
    }
}
