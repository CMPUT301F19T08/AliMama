package com.example.alimama;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;

import com.example.alimama.R;
import com.example.alimama.TestFriendshipOperation;
import com.example.alimama.adapter.FriendPageAdapter;
import com.example.alimama.model.Contact;

public class FriendPageFragment extends Fragment implements FriendshipOperationFeedback {
    String userName;
    View view;
    RecyclerView recyclerView;
    FriendPageAdapter friendPageAdapter;
    Database db;
    Button acceptButton;

    private ArrayList<String> contactDataList;


    public static final String[] contactList = {"PYTHON","JAVA","C","R","C#","SWIFT"};
    public FriendPageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_recycler,container,false);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        friendPageAdapter = new FriendPageAdapter(contactDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        recyclerView.setAdapter(friendPageAdapter);
        acceptButton = view.findViewById(R.id.friend_accept);
        friendPageAdapter.notifyDataSetChanged();
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactDataList = new ArrayList<>();
        for (int i=0;i<contactList.length;i++){
            contactDataList.add(contactList[i]);
        }

        /*acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/





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
        db = new Database();
        db.retrieveCurrentFriendsOfAParticipant("xhou1",this);
        contactDataList = new ArrayList<>();
        for (String each: currentFriendsOfAParticipant){

            contactDataList.add(each);

        }

        friendPageAdapter.notifyDataSetChanged();
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
