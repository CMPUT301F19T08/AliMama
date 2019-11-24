package com.example.alimama.moodHistory;

import com.example.alimama.Model.MoodEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class MoodHistoryPresenter implements MoodHistoryContract.Presenter {

    private MoodHistoryContract.View view;


    private final int STATE_MY_HISTORY = 0;
    private final int STATE_FRIENDS_HISTORY = 1;
    private int CURRENT_STATE = STATE_MY_HISTORY;

    private String currentEmoticon = "\uD83D\uDE0A"; //happy
    private String currentUsername;


    private FirebaseFirestore db;


    public MoodHistoryPresenter(MoodHistoryContract.View view, String currentUsername) {
        this.view = view;
        this.currentUsername = currentUsername;
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

    }



    /**
     * This function retrieves all MoodEvents of current logged-in Participant from database.
     * Result of the retrieval process will be passed through callback functions
     * defined in MoodEventManipulationFeedback interface
     * @param username username of current logged-in Participant
     * @param mmf a reference to an implementation of MoodEventManipulationFeedback interface
     *
     *
     * */
    private void retrieveAllMoodEventsOfAParticipant(final String username) {

        db.collection("MoodEvents")
                .whereEqualTo("username", username)
                .orderBy("date", Query.Direction.DESCENDING )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<MoodEvent> moodEventHistory = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MoodEvent each = document.toObject(MoodEvent.class);
                                moodEventHistory.add(each);
                            }

                            retrieveAllMoodEventOfAParticipantSuccessfully(moodEventHistory);
                        }
                        else {
                            failToRetrieveAllMoodEventOfAParticipant(task.getException().getMessage());
                        }
                    }
                });

    }


    /**
     * This function deletes a MoodEvent of current logged-in Participant from database.
     * Result of the delete process will be passed through callback functions
     * defined in MoodEventManipulationFeedback interface
     * @param moodEvent a MoodEvent object to be deleted
     * @param mmf a reference to an implementation of MoodEventManipulationFeedback interface
     *
     *
     * */

    private void deleteAMoodEventOfAParticipant(final MoodEvent moodEvent) {
        db.collection("MoodEvents")
                .document(moodEvent.getDocumentId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        deleteAMoodEventOfAParticipantSuccessfully();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failToDeleteAMoodEventOfAParticipant(e.getMessage());
                    }
                });

    }


    /**
     * This function retrieves most recent MoodEvents of all existing friends of current logged-in Participant from database
     * Result of the retrieval process will be passed through callback functions
     * defined in MoodEventManipulationFeedback interface
     * @param username username of current logged-in Participant
     * @param mmf a reference to an implementation of MoodEventManipulationFeedback interface
     *
     *
     * */
    private void retrieveMostRecentMoodEventOfFriendsOfAParticipant(String username) {

        CollectionReference friendsCollectionReferece = db.collection("Friends");
        friendsCollectionReferece.whereEqualTo("username", username).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // retrieve a list of friends of a participant
                            final ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipant = new ArrayList<>();
                            CollectionReference moodEventCollectionReference = db.collection("MoodEvents");


                            // for every friend retrieve the most recent moodEvent
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                String friend  = document.getString("friend");
                                moodEventCollectionReference.whereEqualTo("username", friend)
                                        .orderBy("date", Query.Direction.DESCENDING)
                                        .limit(1)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    // problematic implementation
                                                    for (QueryDocumentSnapshot document: task.getResult()) {
                                                        mostRecentMoodEventsOfFriendsOfAParticipant.add(document.toObject(MoodEvent.class));
                                                    }
                                                    retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(mostRecentMoodEventsOfFriendsOfAParticipant);

                                                }
                                                else {
                                                    failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(task.getException().getMessage());
                                                }

                                            }
                                        });


                            }// for every friend, retrieve the most recent Mood request
                        }
                        else {
                            failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(task.getException().getMessage());
                        }
                    }
                });

    }


    @Override
    public void onMoodItemSelected(String emoticon) {
        currentEmoticon = emoticon;
        getMoodEvents();
    }

    @Override
    public void onMyHistoryClicked() {
        CURRENT_STATE = STATE_MY_HISTORY;
        view.selectMyHistoryButton();
        retrieveAllMoodEventsOfAParticipant(currentUsername);
    }

    @Override
    public void onFriendsHistoryButtonClicked() {
        CURRENT_STATE = STATE_FRIENDS_HISTORY;
        view.selectFriendsHistoryButton();
        retrieveMostRecentMoodEventOfFriendsOfAParticipant(currentUsername);

    }

    @Override
    public void onMoodHistoryViewResumed() {
        if (CURRENT_STATE == STATE_MY_HISTORY) view.selectMyHistoryButton();
        else view.selectFriendsHistoryButton();
        getMoodEvents();
    }

    @Override
    public void onMoodItemDeleteClicked(MoodEvent event) {
        deleteAMoodEventOfAParticipant(event);
    }


    private void getMoodEvents() {
        if (CURRENT_STATE == STATE_MY_HISTORY) {
            retrieveAllMoodEventsOfAParticipant(currentUsername);
        } else if (CURRENT_STATE == STATE_FRIENDS_HISTORY) {
            retrieveMostRecentMoodEventOfFriendsOfAParticipant(currentUsername);
        }
    }


    private void retrieveAllMoodEventOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventHistory) {
        view.showMoodEventsList(moodEventHistory, currentEmoticon, false);
    }


    private void deleteAMoodEventOfAParticipantSuccessfully() {
        getMoodEvents();
    }


    private void retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipant) {
        view.showMoodEventsList(mostRecentMoodEventsOfFriendsOfAParticipant, currentEmoticon, true);
    }








    private void failToRetrieveAllMoodEventOfAParticipant(String errmsg) {

    }


    private void failToDeleteAMoodEventOfAParticipant(String errmsg) {

    }


    private void failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(String message) {

    }



}
