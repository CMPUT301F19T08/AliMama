package com.example.alimama;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Text;

import static org.junit.Assert.assertEquals;

public class RequestPageActivityTest {
    private Solo friendpage;
    private TextView textView;
    RecyclerView recyclerView;
    CardView cardView;


    @Rule
    public ActivityTestRule<FriendPageActivity> rule = new ActivityTestRule<FriendPageActivity>(FriendPageActivity.class,true,true){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("USERNAME","xhou1");
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




    public void seekRequest(){
        friendpage.clickOnText("Requests");


        recyclerView = (RecyclerView) friendpage.getView(R.id.my_recycler_view);
        View view = recyclerView.getChildAt(0);
        cardView = view.findViewById(R.id.card_view);

        textView =  cardView.findViewById(R.id.friend_name);
        String name  = textView.getText().toString();
        System.out.println(name);
        assertEquals(name,"sky1");
        Button button = (Button)view.findViewById(R.id.friend_accept);
        friendpage.clickOnView(button);

        System.out.println(name);



    }
    @Test
    public void FriendRequestSend(){

//        tabworking();
        seekRequest();


    }


    @After
    public void tearDown() throws Exception{
        friendpage.finishOpenedActivities();
    }
}
