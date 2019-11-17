package com.example.alimama.mapview;

import com.example.alimama.Model.MoodEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * This class is the implementation of MoodMapContract.MoodMapPresenter interface
 * it's the presenter of MoodMap screen.
 * It's to conform the Model-View-Presenter design Pattern
 * No outstanding issues identified
 *
 * */
public class MoodMapPresenter implements MoodMapContract.MoodMapPresenter, MapViewFeedback {

    private FirebaseFirestore db;
    private MoodMapContract.MoodMapView mMoodMapView;
    private String currentLoggedInParticipant;

    /**
     * class constructor
     * */
    public MoodMapPresenter(MoodMapContract.MoodMapView view) {
        this.mMoodMapView = view;
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
    }


    /**
     * This function retrieves all MoodEvent of current logged-in Participant that have locations from database
     * Result of the retrieval process will be passed through callback functions
     * defined in MapViewFeedback interface
     * @param username username of current logged-in Participant
     * @param mvf a reference to an implementation of MapViewFeedback interface
     *
     *
     * */
    void retrieveAllLocatedMoodEventsOfAParticipant(String username, final MapViewFeedback mvf ) {
        CollectionReference moodEventCollectionRef = db.collection("MoodEvents");
        moodEventCollectionRef.whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<MoodEvent> moodEventsWithLocation = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                MoodEvent currMoodEvent = document.toObject(MoodEvent.class);
                                if (currMoodEvent.getLocationOfMoodEvent() != null) {
                                    moodEventsWithLocation.add(currMoodEvent);
                                }
                            }
                            mvf.retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(moodEventsWithLocation);
                        }
                        else {
                            mvf.failRetrieveAllLocatedMoodEventsOfAParticipant(task.getException().getMessage());

                        }

                    }
                });


    }


    /**
     * This function retrieves most recent MoodEvents of friends of current logged-in Participant that have locations from database
     * Result of the retrieval process will be passed through callback functions
     * defined in MapViewFeedback interface
     * @param username username of current logged-in Participant
     * @param mvf a reference to an implementation of MapViewFeedback interface
     *
     *
     * */

    void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String username, final  MapViewFeedback mvf) {

        CollectionReference friendsCollectionReferece = db.collection("Friends");
        friendsCollectionReferece.whereEqualTo("username", username).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // retrieve a list of friends of a participant
                            final ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation = new ArrayList<>();
                            CollectionReference moodEventCollectionReference = db.collection("MoodEvents");


                            // for every friend retrieve the most recent moodEvent
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                String friend  = document.getString("friend");
                                moodEventCollectionReference.whereEqualTo("username", friend)
                                        .orderBy("date", Query.Direction.DESCENDING)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    // problematic implementation
                                                    for (QueryDocumentSnapshot document: task.getResult()) {
                                                        MoodEvent currMoodEvent = document.toObject(MoodEvent.class);
                                                        if (currMoodEvent.getLocationOfMoodEvent() != null){
                                                            mostRecentMoodEventsOfFriendsOfAParticipantWithLocation.add(currMoodEvent);
                                                            break;
                                                        }

                                                    }
                                                    mvf.retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(mostRecentMoodEventsOfFriendsOfAParticipantWithLocation);

                                                }
                                                else {
                                                    mvf.failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(task.getException().getMessage());
                                                }

                                            }
                                        });


                            }// for every friend, retrieve the most recent Mood request
                        }
                        else {
                            mvf.failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(task.getException().getMessage());
                        }
                    }
                });




    }

    /**
     * this function is the callback when successfully retrieving all MoodEvent of a participant that has location recorded
     * @param moodEventsWithLocation query result from database
     * */
    @Override
    public void retrieveAllLocatedMoodEventsOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventsWithLocation) {
        this.mMoodMapView.clearMap();
        String dateformat = "MM/dd/yyyy HH:mm:ss";
        DateFormat dateF = new SimpleDateFormat(dateformat);
        for(MoodEvent each: moodEventsWithLocation) { // get user's emotion
            if (each.getEmoticon() != null) {
                GeoPoint location = each.getLocationOfMoodEvent();
                String date = dateF.format(each.getDate());
                this.mMoodMapView.createMarker(each.getUsername(), date, location.getLatitude(), location.getLongitude(), each.getEmoticon());

            }
        }

    }

    /**
     * this function is the callback when failed to retrieve all MoodEvent of a participant that has location recorded
     * @param message error message
     * */
    @Override
    public void failRetrieveAllLocatedMoodEventsOfAParticipant(String message) {
        this.mMoodMapView.displayLocatedMoodEventDatabaseErrorToast(message);

    }

    /**
     * this function is the callback when successfully retrieving most recent MoodEvent of friends of a participant that has location recorded
     * @param mostRecentMoodEventsOfFriendsOfAParticipantWithLocation query result from database
     * */
    @Override
    public void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) {
        this.mMoodMapView.clearMap();
        String dateformat = "MM/dd/yyyy HH:mm:ss";
        DateFormat dateF = new SimpleDateFormat(dateformat);
        for(MoodEvent each: mostRecentMoodEventsOfFriendsOfAParticipantWithLocation) { // get user's emotion
            if (each.getEmoticon() != null) {
                GeoPoint location = each.getLocationOfMoodEvent();
                String date = dateF.format(each.getDate());
                this.mMoodMapView.createMarker(each.getUsername(), date, location.getLatitude(), location.getLongitude(), each.getEmoticon());

            }
        }

    }

    /**
     * this function is the callback when failed to retrieve most recent MoodEvent of friends of a participant that has location recorded
     * @param message error message
     * */
    @Override
    public void failRetrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(String message) {
        this.mMoodMapView.displayLocatedMoodEventDatabaseErrorToast(message);

    }

    /**
     * this method retrieves all located most recent MoodEvent of friends of a participant from database
     * */
    @Override
    public void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant() {
        retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant(this.currentLoggedInParticipant, this);
    }

    /**
     * this method retrieves all located MoodEvent of current logged-in participant from database
     * */
    @Override
    public void retrieveAllLocatedMoodEventsOfAParticipant() {
        retrieveAllLocatedMoodEventsOfAParticipant(this.currentLoggedInParticipant, this);

    }

    /**
     * this method will set currentLoggedUser to the value passed in
     * @param currentLoggedInParticipant set currentLoggedInParticipant to newly passed value
     * */
    @Override
    public void setCurrentLoggedInParticipant(String currentLoggedInParticipant) {
        this.currentLoggedInParticipant = currentLoggedInParticipant;

    }


}
