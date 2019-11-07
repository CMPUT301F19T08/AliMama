package com.example.alimama;

import com.example.alimama.Model.MoodEvent;

import java.util.ArrayList;

public class TestMapViewOperation implements MapViewFeedback {


    private Database db;
    TestMapViewOperation() {
        this.db = new Database();

    }
     void retrieveAllLocatedMoodEventsOfAParticipant(String username){
        db.retrieveAllLocatedMoodEventsOfAParticipant(username, this);
    }
     void retrieveAllLocatedMoodEventsOfFriendsOfAParticipant(String username) {
        db.retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(username, this);
    }
    @Override
    public void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation) {
        for (MoodEvent each : moodEventsWithLocation) {
            System.out.println(each);
        }
    }

    @Override
    public void failRetrieveAllLocatedMoodEventsOfAParticipant(String message) {
        System.out.println(message);

    }

    @Override
    public void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
        for (MoodEvent each : mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
            System.out.println(each);
        }

    }

    @Override
    public void failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String message) {

        System.out.println(message);
    }
}
