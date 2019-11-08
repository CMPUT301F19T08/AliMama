package com.example.alimama;

import com.example.alimama.Model.MoodEvent;

import java.util.ArrayList;

/**
 * Should be implemented by classes that require Map View Operation part of the DatabaseUtil API
 * in order to receive return results from database
 * */
public interface MapViewFeedback {
    void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation);

    void failRetrieveAllLocatedMoodEventsOfAParticipant(String message);

    void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation);

    void failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String message);
}