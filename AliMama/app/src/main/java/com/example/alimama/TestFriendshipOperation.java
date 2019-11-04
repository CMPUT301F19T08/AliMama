package com.example.alimama;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

public class TestFriendshipOperation implements  FriendshipOperationFeedback {

    private Database db;
    TestFriendshipOperation() {
        this.db = new Database();
    }




    void retrieveCurrentFriendsOfAParticipant(String username) {
        db.retrieveCurrentFriendsOfAParticipant(username ,this);
    }


    void sendFriendRequestFromCurrentParticipant(String from, String to) {
        db.sendFriendRequestFromCurrentParticipant(from, to, this);
    }

    void retrieveAllPendingFriendRequestsOfAParticipant(String username) {
        db.retrievePendingFriendRequestOfAParticipant(username, this);
    }


    void retrieveAListOfParticipantsToAdd(String username) {
        db.retrieveAListOfParticipantsToAdd(username, this);
    }

    void acceptAFriendRequestOfAParticipant(String username, String newFriend) {
        db.acceptAFriendRequestOfAParticipant(username, newFriend, this);
    }











    @Override
    public void retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(ArrayList<String>pendingFriendRequests) {

        Log.d("IDA", "retrieveAllPendingFriendRequestsOfAParticipantSuccessfully");
        for (String each: pendingFriendRequests)
            System.out.println(each);

    }

    @Override
    public void failRetrieveAllPendingFriendRequestsOfAParticipant(String message) {
        Log.d("IDA", message);

    }

    @Override
    public void acceptAFriendRequestOfAParticipantSuccessfully() {
        Log.d("IDA", "acceptAFriendRequestOfAParticipantSuccessfully");
        retrieveCurrentFriendsOfAParticipant("xhou1");
        retrieveAListOfParticipantsToAdd("xhou1");

    }

    @Override
    public void failAcceptAFriendRequestOfAParticipant(String message) {
        Log.d("IDA", message);
    }

    @Override
    public void retrieveCurrentFriendsOfAParticipantSuccessfully(ArrayList<String> currentFriendsOfAParticipant) {
        Log.d("IDA", "retrieveCurrentFriendsOfAParticipantSuccessfully");
        for (String each: currentFriendsOfAParticipant) {
            System.out.println(each);
        }

    }

    @Override
    public void failRetrieveCurrentFriendsOfAParticipant(String message) {
        Log.d("IDA", message);
    }

    @Override
    public void retrieveAListOfParticipantsToAddSuccessfully(HashSet<String> existingParticipants) {
        System.out.println("AListOfParticipantsToAdd");
        for (String each: existingParticipants) {System.out.println(each);}
    }

    @Override
    public void failRetrieveAListOfParticipantsToAdd(String message) {
        Log.d("IDA", message);
    }

    @Override
    public void sendFriendRequestFromCurrentParticipantSuccessfully() {
        Log.d("IDA", "sendFriendRequestFromCurrentParticipantSuccessfully");


    }

    @Override
    public void failSendFriendRequestFromCurrentParticipant(String message) {
        Log.d("IDA", message);

    }
}
