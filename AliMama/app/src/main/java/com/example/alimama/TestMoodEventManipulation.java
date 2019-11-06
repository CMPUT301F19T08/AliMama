package com.example.alimama;

import android.util.Log;

import com.example.alimama.Model.MoodEvent;

import java.util.ArrayList;

public class TestMoodEventManipulation implements MoodEventManipulationFeedback{

    private Database db;


    TestMoodEventManipulation() {
        this.db = new Database();
    }

    public void registerMoodEventRealTimeListener(String username) {
        db.registerMoodEventRealTimeListener(username, this);
    }

    public void addMood(MoodEvent moodEvent) {

        db.addANewMoodEvent(moodEvent, this);
    }
    public void retrieveAllMoodGivenUser(String username) {
        db.retrieveAllMoodEventsOfAParticipant(username, this);
    }

    public void deleteAnExistingMood(MoodEvent moodEvent) {

        db.deleteAMoodEventOfAParticipant(moodEvent, this);

    }

    public void retrieveMostRecentMoodEventOfFriends(String username) {
        db.retrieveMostRecentMoodEventOfFriendsOfAParticipant(username, this);
    }







    @Override
    public void failToUpdateAnExistingMoodEvent(String errmsg) {

    }

    @Override
    public void updateAnExistingMoodEventSuccessfully() {


    }

    @Override
    public void failToAddANewMoodEvent(String errmsg) {
        Log.d("IDA","failToAddANewMoodEvent " + errmsg );

    }

    @Override
    public void addANewMoodEventSuccessfully() {
        Log.d("IDA", "faddANewMoodEventSuccessfully\n");

    }

    @Override
    public void retrieveAllMoodEventOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventHistory) {
        if (moodEventHistory.size() == 0) {
            Log.d("IDA", "MoodEvents Of Current Participant is empty");
            return;

        }

        for (MoodEvent each: moodEventHistory) {
            Log.d("IDA", each.toString());
            each.setSocialSituation("I am to be deleted");


        }

    }

    @Override
    public void failToRetrieveAllMoodEventOfAParticipant(String errmsg) {
        Log.d("IDA", "failToRetrieveAllMoodEventOfAParticipant" + errmsg);


    }

    @Override
    public void deleteAMoodEventOfAParticipantSuccessfully() {
        Log.d("IDA","deleted the mood successfully" );

    }

    @Override
    public void failToDeleteAMoodEventOfAParticipant(String errmsg) {
        Log.d("IDA","failToDeleteAMoodEventOfAParticipant " + errmsg );

    }

    @Override
    public void failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(String message) {
        System.out.println(message);

    }

    @Override
    public void retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipant) {
        for (MoodEvent each: mostRecentMoodEventsOfFriendsOfAParticipant) {
            System.out.println(each);
        }

    }

    @Override
    public void failRegisterMoodEventRealTimeListener(String message) {
        Log.e("IDA", message);
    }
}
