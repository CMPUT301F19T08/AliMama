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

/**
 * This is the class that connects the database for with the mood history class.
 */

public class MoodHistoryPresenter implements MoodHistoryContract.Presenter {

    private MoodHistoryContract.View view;


    private final int STATE_MY_HISTORY = 0;
    private final int STATE_FRIENDS_HISTORY = 1;
    private int CURRENT_STATE = STATE_MY_HISTORY;

    private String currentEmoticon = "\uD83D\uDE0A"; //happy
    private String currentUsername;


    private FirebaseFirestore db;


    /**
     * connects to the database
     * @param view
     * @param currentUsername
     *
     * */
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
     *
     * */
    private void retrieveMostRecentMoodEventOfFriendsOfAParticipant(String username) {

        CollectionReference friendsCollectionReferece = db.collection("Friends");
        friendsCollectionReferece.whereEqualTo("username", username).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() == 0) {
                                retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(new ArrayList());

                            }
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


    /**
     * gets the emoticon selected of emotional spinner in the AddEdit class
     * @param emoticon
     * */
    @Override
    public void onMoodItemSelected(String emoticon) {
        currentEmoticon = emoticon;
        getMoodEvents();
    }

    //retrieves all mood of the logged in user when on the My History tab
    @Override
    public void onMyHistoryClicked() {
        CURRENT_STATE = STATE_MY_HISTORY;
        view.selectMyHistoryButton();
        retrieveAllMoodEventsOfAParticipant(currentUsername);
    }

    //retrieves all mood of the logged in user's friends when on the Friends History tab
    @Override
    public void onFriendsHistoryButtonClicked() {
        CURRENT_STATE = STATE_FRIENDS_HISTORY;
        view.selectFriendsHistoryButton();
        retrieveMostRecentMoodEventOfFriendsOfAParticipant(currentUsername);

    }

    // continue viewing MoodHistory
    @Override
    public void onMoodHistoryViewResumed() {
        if (CURRENT_STATE == STATE_MY_HISTORY) view.selectMyHistoryButton();
        else view.selectFriendsHistoryButton();
        getMoodEvents();
    }

    /**
     * listens for when the delete button is clicked so that the selected mood is deleted
     * @param event
     * */
    @Override
    public void onMoodItemDeleteClicked(MoodEvent event) {
        deleteAMoodEventOfAParticipant(event);
    }


    //get all the current moods in the database depending if on the My History tab or the Friends Hisotry tab
    private void getMoodEvents() {
        if (CURRENT_STATE == STATE_MY_HISTORY) {
            retrieveAllMoodEventsOfAParticipant(currentUsername);
        } else if (CURRENT_STATE == STATE_FRIENDS_HISTORY) {
            retrieveMostRecentMoodEventOfFriendsOfAParticipant(currentUsername);
        }
    }


    /**
     * retrieves all the mood events of the participant
     * @param moodEventHistory
     * */
    private void retrieveAllMoodEventOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventHistory) {
        view.showMoodEventsList(moodEventHistory, currentEmoticon, false);
    }


    //checks to make sure the mood selected is deleted by getting all the moods
    private void deleteAMoodEventOfAParticipantSuccessfully() {
        getMoodEvents();
    }


    /**
     * retrieves all the mood events of the participants friends mood history
     * @param mostRecentMoodEventsOfFriendsOfAParticipant
     * */
    private void retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipant) {
        view.showMoodEventsList(mostRecentMoodEventsOfFriendsOfAParticipant, currentEmoticon, true);
    }






    /**
     * The following are error messages to be displayed when we cannot either, retrieve the mood events of the user,
     * fail to delete the selected mood, or fail to retrieve all mood of friends
     * */

    /**error message when failed to retrieve all mood events from database for current logged on user
     * @param errmsg error message to be displayed
     * */
    private void failToRetrieveAllMoodEventOfAParticipant(String errmsg) {

    }


    /**error message when failed to delete selected mood events from database
     *  @param errmsg error message to be displayed
     * */
    private void failToDeleteAMoodEventOfAParticipant(String errmsg) {

    }

    /**error message when failed to retrieve all mood events from database for current logged on user's friends history
     *  @param message error message to be displayed
     * */
    private void failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(String message) {

    }



}
