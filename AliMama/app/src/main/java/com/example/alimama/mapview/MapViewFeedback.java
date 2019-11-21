package com.example.alimama.mapview;

import com.example.alimama.Model.MoodEvent;

import java.util.ArrayList;

/**
 * Should be implemented by classes that require Map View Operation part of the database functionalities
 * in order to receive return results from database
 * No outstanding issues identified
 * */
public interface MapViewFeedback {
    /**
     * this function is the callback when successfully retrieving all MoodEvent of a participant that has location recorded
     * @param moodEventsWithLocation query result from database
     * */
    void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation);

    /**
     * this function is the callback when failed to retrieve all MoodEvent of a participant that has location recorded
     * @param message error message
     * */
    void failRetrieveAllLocatedMoodEventsOfAParticipant(String message);

    /**
     * this function is the callback when successfully retrieving most recent MoodEvent of friends of a participant that has location recorded
     * @param mostRecentMoodEventsOfFriendsOfAParticipantWithLocation query result from database
     * */
    void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation);

    /**
     * this function is the callback when failed to retrieve most recent MoodEvent of friends of a participant that has location recorded
     * @param message error message
     * */
    void failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String message);
}