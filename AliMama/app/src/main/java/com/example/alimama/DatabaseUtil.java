package com.example.alimama;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.alimama.Model.MoodEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



/**
 * DatabaseUtil class is a custom API that aims to provide an easy-to-use interface to Firestore backend.
 * DatabaseUtil class adopts Singleton design pattern to ensure that only one instance of the DatabaseUtil object
 * will exist throughout the application life cycle.
 *
 * @author Xuechun Hou
 * */
public class DatabaseUtil {

    private static FirebaseFirestore db = null;



    public DatabaseUtil(FirebaseFirestore fdb) {
        db = fdb;
    }
    /**
     * Class Constructor
     *
     * */
    public DatabaseUtil() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
        FirebaseFirestore.setLoggingEnabled(true);
    }

    /* -------------------------------- MoodEvent Manipulation ----------------------------------*/

    /**
     * This function retrieves MoodEvent of a participant identified with username real time which
     * means that this function will be called as soon as there is updates to the MoodEvent of the participant in the database.
     *
     * @param username username of current logged-in Participant
     * @param mmf a reference to an implementation of MoodEventManipulationFeedback interface
     *
     *
     * */

    void registerMoodEventRealTimeListener(String username, final MoodEventManipulationFeedback mmf) {
        db.collection("MoodEvents")
                .whereEqualTo("username", username)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {

                            mmf.failRegisterMoodEventRealTimeListener(e.getMessage());
                        }

                        ArrayList<MoodEvent> moodEvents = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            moodEvents.add(doc.toObject(MoodEvent.class));

                        }
                        mmf.retrieveAllMoodEventOfAParticipantSuccessfully(moodEvents);

                    }

                });

    }


    /**
     * This function updates an MoodEvent of the current logged-in Participant. Result of the update process will be passed through callback functions
     * defined in MoodEventManipulationFeedback interface
     * @param moodEvent a MoodEvent object to be updated
     * @param mmf a reference to an implementation of MoodEventManipulationFeedback interface
     *
     *
     * */
    void updateAnExistingMoodEvent(final MoodEvent moodEvent, final MoodEventManipulationFeedback mmf) {
        db.collection("MoodEvents")
                .document(moodEvent.getDocumentId())
                .set(moodEvent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mmf.updateAnExistingMoodEventSuccessfully();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mmf.failToUpdateAnExistingMoodEvent(e.getMessage());
                    }
                });


    }


    /**
     * This function adds a new MoodEvent created by current logged-in Participant's to database.
     * Result of the add process will be passed through callback functions
     * defined in MoodEventManipulationFeedback interface
     * @param newMoodEvent a new MoodEvent object to be added
     * @param mmf a reference to an implementation of MoodEventManipulationFeedback interface
     *
     *
     * */
    void addANewMoodEvent(final MoodEvent newMoodEvent, final MoodEventManipulationFeedback mmf) {

        db.collection("MoodEvents")
                .add(newMoodEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("successfully added a mood event");
                        mmf.addANewMoodEventSuccessfully();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getMessage());
                        mmf.failToAddANewMoodEvent(e.getMessage());

                    }
                });


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
    void retrieveAllMoodEventsOfAParticipant(final String username, final MoodEventManipulationFeedback mmf) {

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

                            mmf.retrieveAllMoodEventOfAParticipantSuccessfully(moodEventHistory);
                        }
                        else {
                            mmf.failToRetrieveAllMoodEventOfAParticipant(task.getException().getMessage());
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

    void deleteAMoodEventOfAParticipant(final MoodEvent moodEvent, final MoodEventManipulationFeedback mmf) {
        db.collection("MoodEvents")
                .document(moodEvent.getDocumentId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mmf.deleteAMoodEventOfAParticipantSuccessfully();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mmf.failToDeleteAMoodEventOfAParticipant(e.getMessage());
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
    void retrieveMostRecentMoodEventOfFriendsOfAParticipant(String username, final MoodEventManipulationFeedback mmf) {

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
                                                    mmf.retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(mostRecentMoodEventsOfFriendsOfAParticipant);

                                                }
                                                else {
                                                    mmf.failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(task.getException().getMessage());
                                                }

                                            }
                                        });


                            }// for every friend, retrieve the most recent Mood request
                        }
                        else {
                            mmf.failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(task.getException().getMessage());
                        }
                    }
                });

    }
