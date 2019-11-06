package com.example.alimama;

import com.example.alimama.Model.MoodEvent;

import java.util.ArrayList;

/**
 * Should be implemented by classes that require MoodEvent Manipulation(Add Edit Delete) part of the Database API
 * in order to receive return results from database
 * */

public interface MoodEventManipulationFeedback {
    void failToUpdateAnExistingMoodEvent(String errmsg);

    void updateAnExistingMoodEventSuccessfully();

    void failToAddANewMoodEvent(String errmsg);

    void addANewMoodEventSuccessfully();

    void retrieveAllMoodEventOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventHistory);

    void failToRetrieveAllMoodEventOfAParticipant(String errmsg);

    void deleteAMoodEventOfAParticipantSuccessfully();

    void failToDeleteAMoodEventOfAParticipant(String errmsg);

    void failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(String message);

    void retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipant);

    void failRegisterMoodEventRealTimeListener(String message);
}
