package com.example.alimama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import com.example.alimama.R;

import com.example.alimama.ui.main.TabPageAdapter;

import java.util.ArrayList;
import java.util.HashSet;

public class FriendPage extends AppCompatActivity implements FriendshipOperationFeedback{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ViewPager viewContact;
    private ViewPager viewFriend;
    private ViewPager viewRequest;

    private ViewPager viewPager;

    private TabPageAdapter tabPageAdapter;

    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tmp_tab_view);

        tabPageAdapter = new TabPageAdapter(this,getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        /*Fragments*/

        tabPageAdapter.addFragment(new ContactPageFragment(),"Contact Page");
        tabPageAdapter.addFragment(new FriendPageFragment(),"Friend Page");
        tabPageAdapter.addFragment(new RequestPageFragment(),"Request Page");

        viewPager.setAdapter(tabPageAdapter);



        tabLayout =findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);







         /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }*/


    }





    @Override
    public void retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(ArrayList<String> pendingFriendRequests) {
        FriendPageFragment fpf = (FriendPageFragment) this.tabPageAdapter.getItem(1);
        fpf.setAdapterData(pendingFriendRequests);
        fpf.getFriendPageAdapter().notifyDataSetChanged();



    }

    @Override
    public void failRetrieveAllPendingFriendRequestsOfAParticipant(String message) {

    }

    @Override
    public void acceptAFriendRequestOfAParticipantSuccessfully() {


        FriendPageFragment fpf = (FriendPageFragment) this.tabPageAdapter.getItem(1);

        fpf.getFriendPageAdapter().notifyDataSetChanged();



    }

    @Override
    public void failAcceptAFriendRequestOfAParticipant(String message) {

    }

    @Override
    public void retrieveCurrentFriendsOfAParticipantSuccessfully(ArrayList<String> currentFriendsOfAParticipant) {

    }

    @Override
    public void failRetrieveCurrentFriendsOfAParticipant(String message) {

    }

    @Override
    public void retrieveAListOfParticipantsToAddSuccessfully(HashSet<String> existingParticipants) {

    }

    @Override
    public void failRetrieveAListOfParticipantsToAdd(String message) {

    }

    @Override
    public void sendFriendRequestFromCurrentParticipantSuccessfully() {

    }

    @Override
    public void failSendFriendRequestFromCurrentParticipant(String message) {

    }
}
