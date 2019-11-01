package com.example.alimama;

import java.util.ArrayList;

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
