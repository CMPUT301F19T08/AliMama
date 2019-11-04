package com.example.alimama;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;


import com.example.alimama.R;
import com.example.alimama.adapter.ContactPageAdapter;



public class ContactPageFragment extends Fragment implements FriendshipOperationFeedback {
    View view;

    private RecyclerView recyclerView;
    private ArrayList<String> contactDataList;
    private String contractName;


    public static final String[] contactList = {"ALPHA","BETA","GAMMA","DELTA","THETA","OMEGA","ZETA"};

    public ContactPageFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.view_recycler,container,false);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        ContactPageAdapter contactPageAdapter = new ContactPageAdapter(contactDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(contactPageAdapter);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*contactDataList = new ArrayList<>();

        contactDataList.add(new Contact("ALPHA"));
        contactDataList.add(new Contact("BETA"));
        contactDataList.add(new Contact("OMEGA"));*/
        /*contactDataList = new ArrayList<>();
        for(int i=0;i<contactList.length;i++){

            contactDataList.add((new Contact(contactList[i])));


        }*/




    }

    @Override
    public void retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(ArrayList<String> pendingFriendRequests) {

    }

    @Override
    public void failRetrieveAllPendingFriendRequestsOfAParticipant(String message) {

    }

    @Override
    public void acceptAFriendRequestOfAParticipantSuccessfully() {

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
