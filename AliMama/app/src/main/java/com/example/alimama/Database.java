package com.example.alimama;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


class Database {

    private static FirebaseFirestore db = null;


    Database() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
        FirebaseFirestore.setLoggingEnabled(true);
    }

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

    // this function retrieves MoodEvent of a participant identified with username real time which
    // means that this function will be called as soon as there is updates to the MoodEvent of the participant.
    // notifyDatasetChanged could be called within callback function retrieveAllMoodEventOfAParticipantSuccessfully
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

    // delete the friend request sent by "usernameOfFriendRequestToAccept" participant to the current participant identified by "username" from PendingFriendRequests Collection
    // then Add to Friends Collection a new Document with username "username" and friend "usernameOfFriendRequestToAccept"
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
                                                Map<String, String> friendDocument = new HashMap<>();
                                                friendDocument.put("username" , username);
                                                friendDocument.put("friend", usernameOfFriendRequestToAccept);
                                                friendsCollectionRef.add(friendDocument)
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
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

    // this function retrieves MoodEvent of a participant identified with username real time which
    // means that this function will be called as soon as there is updates to the MoodEvent of the participant.
    // notifyDatasetChanged could be called within callback function retrieveAllMoodEventOfAParticipantSuccessfully
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

    // given username of the current Participant, this function will retrieve A list of Participants that haven't been
    // friend with the current Participant.
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
