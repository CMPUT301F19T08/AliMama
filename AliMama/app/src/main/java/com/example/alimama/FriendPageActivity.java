package com.example.alimama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import com.example.alimama.Controller.TabPageAdapter;

import java.util.ArrayList;
import java.util.HashSet;

public class FriendPageActivity extends AppCompatActivity implements FriendshipOperationFeedback {

    private TabPageAdapter tabPageAdapter;


    private TabLayout tabLayout;

    private int positionContactpage =0;
    private int positionFriendpage = 1;
    private int positionRequestpage = 2;

    private String username;

    /**
     * Create view for the whole page with tabs and fragments
     * */

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




    /**
     * {@inheritDoc}
     * @param pendingFriendRequests
     * retrieve pending friends of the participant
     * pass all pending into ArrayList
     * */

    @Override
    public void retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(ArrayList<String> pendingFriendRequests) {

        RequestPageFragment rpf = (RequestPageFragment) this.tabPageAdapter.getItem(positionRequestpage);
        rpf.setAdapterData(pendingFriendRequests);
        rpf.getRequestPageAdapter().notifyDataSetChanged();


    }


    /**
     * {@inheritDoc}
     * @param message
     * unused
     * */
    @Override
    public void failRetrieveAllPendingFriendRequestsOfAParticipant(String message) {

    }


    /**
     * {@inheritDoc}
     * update view when participant accepts a friend successfully
     * */
    @Override
    public void acceptAFriendRequestOfAParticipantSuccessfully() {

        RequestPageFragment rpf = (RequestPageFragment) this.tabPageAdapter.getItem(positionRequestpage);

        rpf.getRequestPageAdapter().notifyDataSetChanged();



    }

    /**
     * {@inheritDoc}
     * @param message
     * unused
     * */
    @Override
    public void failAcceptAFriendRequestOfAParticipant(String message) {

    }

    /**
     * {@inheritDoc}
     * @param currentFriendsOfAParticipant
     * retrieve friends of participant
     * */
    @Override
    public void retrieveCurrentFriendsOfAParticipantSuccessfully(ArrayList<String> currentFriendsOfAParticipant) {
        ContactPageFragment cpf = (ContactPageFragment) this.tabPageAdapter.getItem(positionContactpage);
        cpf.setAdapterData(currentFriendsOfAParticipant);
        cpf.getContactPageAdapter().notifyDataSetChanged();

    }


    /**
     * {@inheritDoc}
     * @param message
     * */
    @Override
    public void failRetrieveCurrentFriendsOfAParticipant(String message) {

    }

    /**
     * {@inheritDoc}
     * @param existingParticipants
     * retrieve list of
     * */
    @Override
    public void retrieveAListOfParticipantsToAddSuccessfully(HashSet<String> existingParticipants) {
        FriendPageFragment fpf = (FriendPageFragment) this.tabPageAdapter.getItem(positionFriendpage);
        fpf.setAdapterData(existingParticipants);
        fpf.getFriendPageAdapter().notifyDataSetChanged();

    }


    /**
     * {@inheritDoc}
     * @param message
     * unused
     * */
    @Override
    public void failRetrieveAListOfParticipantsToAdd(String message) {

    }


    /**
     * {@inheritDoc}
     * participant send friend request
     * */
    @Override
    public void sendFriendRequestFromCurrentParticipantSuccessfully() {
        FriendPageFragment fpf = (FriendPageFragment) this.tabPageAdapter.getItem(positionFriendpage);

        fpf.getContactPageAdapter().notifyDataSetChanged();

    }


    /**
     * {@inheritDoc}
     * @param message
     * */
    @Override
    public void failSendFriendRequestFromCurrentParticipant(String message) {

    }


}
