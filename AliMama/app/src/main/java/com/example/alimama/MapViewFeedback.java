package com.example.alimama;

import java.util.ArrayList;

public interface MapViewFeedback {
    void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation);

    void failRetrieveAllLocatedMoodEventsOfAParticipant(String message);

    void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation);

    void failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String message);
}
