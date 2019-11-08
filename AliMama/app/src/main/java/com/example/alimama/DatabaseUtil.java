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


    /**
     * Authenticate the given existing Participant.
     * Result of authentication will be passed through callback functions
     * defined in CredentialValidationDelegate interface
     * @param username username of Participant
     * @param password password of Participant
     * @param cvd a reference to an implementation of CredentialValidationDelegate interface
     *
     * */
    /* ------------------------------------- Participant Authentication ------------------------------------*/
    void authenticExistingParticipant(String username, final String password, final CredentialValidationDelegate cvd) {

        // check if username exists
        DocumentReference usernameReference = db.collection("Participants").document(username);
        usernameReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (! document.exists()) {
                                cvd.usernameNotExist();
                            }
                            else {
                                if (password.equals(document.get("password"))) {
                                    cvd.existingParticipantLoginSuccessfully();
                                }else {
                                    cvd.existingParticipantPasswordNotMatch();
                                }
                            }

                        }
                        else {
                            cvd.existingParticipantLoginError(task.getException().getMessage());

                        }
                    }
                });
    }


    /**
     * Perform new Participant sign up. Result of the sign up process will be passed through callback functions
     * defined in CredentialValidationDelegate interface
     * @param username username of new Participant
     * @param password password of new Participant
     * @param cvd a reference to an implementation of CredentialValidationDelegate interface
     *
     * */
    void signupNewParticipant(final String username, final String password, final CredentialValidationDelegate cvd) {

        // check if username exists
        DocumentReference usernameReference = db.collection("Participants").document(username);
        usernameReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if ( document.exists()) {
                                cvd.usernameExist();
                            }
                            else {
                                Map<String, String> credential = new HashMap<>();
                                credential.put("username", username);
                                credential.put("password", password);
                                db.collection("Participants").document(username).set(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    cvd.newParticipantSignupSuccessfully();
                                                }
                                                else {
                                                    cvd.newParticipantSignupError(task.getException().getMessage());
                                                }
                                            }
                                        });


                            }

                        }
                        else {
                            cvd.newParticipantSignupError(task.getException().getMessage());

                        }
                    }
                });

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


    /* -----------------------------Friendship Operations -------------------------------- */


    /**
     * This function retrieves all pending friend requests of current logged-in Participant from database
     * Result of the retrieval process will be passed through callback functions
     * defined in FriendshipOperationFeedback interface
     * @param username username of current logged-in Participant
     * @param fof a reference to an implementation of FriendshipOperationFeedback interface
     *
     *
     * */

    void retrievePendingFriendRequestOfAParticipant(String username, final FriendshipOperationFeedback fof) {
        db.collection("PendingFriendRequests")
                .whereEqualTo("friendToAdd", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> pendingFriendRequests = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                pendingFriendRequests.add(document.getString("username"));
                            }

                            fof.retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(pendingFriendRequests);
                        }
                        else {
                            fof.failRetrieveAllPendingFriendRequestsOfAParticipant(task.getException().getMessage());
                        }
                    }
                });

    }


    /**
     * This function accepts a friend request sent to the current logged-in Participant.
     * Result of the acceptance process will be passed through callback functions
     * defined in FriendshipOperationFeedback interface
     * @param username username of current logged-in Participant
     * @param usernameOfFriendRequestToAccept the username of the Participant who created the friend request
     * @param fof a reference to an implementation of FriendshipOperationFeedback interface
     *
     *
     * */

    void acceptAFriendRequestOfAParticipant(final String username, final String usernameOfFriendRequestToAccept, final FriendshipOperationFeedback fof) {
        final CollectionReference itemsRef = db.collection("PendingFriendRequests");

                itemsRef
                .whereEqualTo("username", username)
                .whereEqualTo("friendToAdd", usernameOfFriendRequestToAccept)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                itemsRef.document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                final CollectionReference friendsCollectionRef = db.collection("Friends");
                                                WriteBatch batch = db.batch();
                                                DocumentReference doc1 = friendsCollectionRef.document();
                                                DocumentReference doc2 = friendsCollectionRef.document();
                                                Map<String, String> friendDocument = new HashMap<>();
                                                friendDocument.put("username" , username);
                                                friendDocument.put("friend", usernameOfFriendRequestToAccept);
                                                doc1.set(friendDocument);
                                                friendDocument.clear();
                                                friendDocument.put("username" , usernameOfFriendRequestToAccept);
                                                friendDocument.put("friend", username);
                                                doc2.set(friendDocument);
                                                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            fof.acceptAFriendRequestOfAParticipantSuccessfully();

                                                        }else {
                                                            fof.failAcceptAFriendRequestOfAParticipant(task.getException().getMessage());

                                                        }
                                                    }
                                                });


                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                fof.failAcceptAFriendRequestOfAParticipant(e.getMessage());

                                            }
                                        });
                            }
                        }
                        else {
                            fof.failAcceptAFriendRequestOfAParticipant(task.getException().getMessage());

                        }
                    }
                });




    }

    /**
     * This function registers a realtime listener of existing friend list of current logged-in Participant.
     * @param username username of current logged-in Participant
     * @param fof a reference to an implementation of FriendshipOperationFeedback interface
     *
     *
     * */
    void registerCurrentFriendsOfAParticipantRealTimeListener(String username, final FriendshipOperationFeedback fof) {
        db.collection("Friends")
                .whereEqualTo("username", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {

                            fof.failRetrieveCurrentFriendsOfAParticipant(e.getMessage());
                        }

                        ArrayList<String> existingFriends = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            existingFriends.add(doc.getString("friend"));

                        }
                        fof.retrieveCurrentFriendsOfAParticipantSuccessfully(existingFriends);

                    }

                });
    }


    /**
     * This function retrieves a list of existing friends of current logged-in Participant from database.
     * Result of the retrieval process will be passed through callback functions
     * defined in FriendshipOperationFeedback interface
     * @param username username of current logged-in Participant
     * @param fof a reference to an implementation of FriendshipOperationFeedback interface
     *
     *
     * */
    void retrieveCurrentFriendsOfAParticipant(final String username, final FriendshipOperationFeedback fof) {
        CollectionReference friendsCollectionReferece = db.collection("Friends");
        friendsCollectionReferece
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> friendsOfAParticipant = new ArrayList<>();
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                friendsOfAParticipant.add(document.getString("friend"));

                            }
                            fof.retrieveCurrentFriendsOfAParticipantSuccessfully(friendsOfAParticipant);


                        }
                        else {
                            fof.failRetrieveCurrentFriendsOfAParticipant(task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * This function retrieves a list of Participants who haven't been friends with current logged-in Participant from database.
     * Result of the retrieval process will be passed through callback functions
     * defined in FriendshipOperationFeedback interface
     * @param username username of current logged-in Participant
     * @param fof a reference to an implementation of FriendshipOperationFeedback interface
     *
     *
     * */
    void retrieveAListOfParticipantsToAdd(final String username, final FriendshipOperationFeedback fof ) {
        CollectionReference participantsCollectionRef = db.collection("Participants");
        participantsCollectionRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            final HashSet<String> existingParticipants = new HashSet<>();
                            for (QueryDocumentSnapshot document :  task.getResult()) {
                                String currUsername = document.getString("username");

                                existingParticipants.add(currUsername);
                            }
                            existingParticipants.remove(username);
                            CollectionReference friendsCollectionRef = db.collection("Friends");
                            friendsCollectionRef.whereEqualTo("username", username)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document: task.getResult()) {
                                                    String currUsername = document.getString("friend");
                                                    existingParticipants.remove(currUsername);
                                                }
                                                fof.retrieveAListOfParticipantsToAddSuccessfully(existingParticipants);


                                            }
                                            else {

                                                fof.failRetrieveAListOfParticipantsToAdd(task.getException().getMessage());
                                            }
                                        }
                                    });


                        }
                        else  {
                            fof.failRetrieveAListOfParticipantsToAdd(task.getException().getMessage());
                        }

                    }
                });
    }



    /**
     * This function initiated a friend request on behalf of current logged-in user and send to the designated participant.
     * Result of the process will be passed through callback functions
     * defined in FriendshipOperationFeedback interface
     * @param currentParticipant username of current logged-in Participant
     * @param participantToBeSentFriendRequest username of recipient Participant
     * @param fof a reference to an implementation of FriendshipOperationFeedback interface
     *
     *
     * */
    void sendFriendRequestFromCurrentParticipant(final String currentParticipant, final String participantToBeSentFriendRequest, final FriendshipOperationFeedback fof) {
        CollectionReference pendingFriendRequestCollectionRef = db.collection("PendingFriendRequests");
        Map<String, String> pendingFriendRequestDocument = new HashMap();
        pendingFriendRequestDocument.put("username", currentParticipant);
        pendingFriendRequestDocument.put("friendToAdd", participantToBeSentFriendRequest);
        pendingFriendRequestCollectionRef
                .add(pendingFriendRequestDocument)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {

                            fof.sendFriendRequestFromCurrentParticipantSuccessfully();
                        }
                        else {
                            fof.failSendFriendRequestFromCurrentParticipant(task.getException().getMessage());
                        }


                    }
                });
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
    /* -----------------------------Map View Operations -------------------------------- */

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









}
