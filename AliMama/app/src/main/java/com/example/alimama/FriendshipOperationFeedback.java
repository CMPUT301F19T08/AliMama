package com.example.alimama;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Should be implemented by classes that require Add Friend /View Friend Requests part of the DatabaseUtil API
 * in order to receive return results from database
 * */
public interface FriendshipOperationFeedback {
    void retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(ArrayList<String> pendingFriendRequests);

    void failRetrieveAllPendingFriendRequestsOfAParticipant(String message);

    void acceptAFriendRequestOfAParticipantSuccessfully();



    void failAcceptAFriendRequestOfAParticipant(String message);

    void retrieveCurrentFriendsOfAParticipantSuccessfully(ArrayList<String> currentFriendsOfAParticipant);

    void failRetrieveCurrentFriendsOfAParticipant(String message);

    void retrieveAListOfParticipantsToAddSuccessfully(HashSet<String> existingParticipants);

    void failRetrieveAListOfParticipantsToAdd(String message);

    void sendFriendRequestFromCurrentParticipantSuccessfully();

    void failSendFriendRequestFromCurrentParticipant(String message);
}