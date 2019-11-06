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

public class FriendPage extends AppCompatActivity implements FriendshipOperationFeedback {

    private TabPageAdapter tabPageAdapter;


    private TabLayout tabLayout;

    private int positionContactpage =0;
    private int positionFriendpage = 1;
    private int positionRequestpage = 2;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tmp_tab_view);
        username  = getIntent().getStringExtra("USERNAME");

        tabPageAdapter = new TabPageAdapter(this,getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        /*Fragments*/

        tabPageAdapter.addFragment(new ContactPageFragment(username ),"Contact Page");
        tabPageAdapter.addFragment(new FriendPageFragment(username ),"Friend Page");
        tabPageAdapter.addFragment(new RequestPageFragment(username ),"Request Page");

        viewPager.setAdapter(tabPageAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }





    @Override
    public void retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(ArrayList<String> pendingFriendRequests) {

        RequestPageFragment rpf = (RequestPageFragment) this.tabPageAdapter.getItem(positionRequestpage);
        rpf.setAdapterData(pendingFriendRequests);
        rpf.getRequestPageAdapter().notifyDataSetChanged();


    }

    @Override
    public void failRetrieveAllPendingFriendRequestsOfAParticipant(String message) {

    }

    @Override
    public void acceptAFriendRequestOfAParticipantSuccessfully() {

        RequestPageFragment rpf = (RequestPageFragment) this.tabPageAdapter.getItem(2);

        rpf.getRequestPageAdapter().notifyDataSetChanged();



    }

    @Override
    public void failAcceptAFriendRequestOfAParticipant(String message) {

    }

    @Override
    public void retrieveCurrentFriendsOfAParticipantSuccessfully(ArrayList<String> currentFriendsOfAParticipant) {
        ContactPageFragment cpf = (ContactPageFragment) this.tabPageAdapter.getItem(0);
        cpf.setAdapterData(currentFriendsOfAParticipant);
        cpf.getContactPageAdapter().notifyDataSetChanged();

    }

    @Override
    public void failRetrieveCurrentFriendsOfAParticipant(String message) {

    }

    @Override
    public void retrieveAListOfParticipantsToAddSuccessfully(HashSet<String> existingParticipants) {
        FriendPageFragment fpf = (FriendPageFragment) this.tabPageAdapter.getItem(1);
        fpf.setAdapterData(existingParticipants);
        fpf.getFriendPageAdapter().notifyDataSetChanged();

    }

    @Override
    public void failRetrieveAListOfParticipantsToAdd(String message) {

    }

    @Override
    public void sendFriendRequestFromCurrentParticipantSuccessfully() {
        FriendPageFragment fpf = (FriendPageFragment) this.tabPageAdapter.getItem(1);

        fpf.getContactPageAdapter().notifyDataSetChanged();

    }

    @Override
    public void failSendFriendRequestFromCurrentParticipant(String message) {

    }


}
