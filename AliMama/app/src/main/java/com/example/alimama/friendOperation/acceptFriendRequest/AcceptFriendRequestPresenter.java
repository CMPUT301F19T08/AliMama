package com.example.alimama.friendOperation.acceptFriendRequest;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * This class is used to accept friend requests.
 * If it fails, then a error message will display.
 * If succeed, two users will have each other in their friends list and the data will update in firestore.
 * And this friend request will be removed.
 * */
public class AcceptFriendRequestPresenter implements AcceptFriendRequestContract.AcceptFriendRequestPresenter {
    /**
     * set the name of current LoggedInParticipant as a string
     * set the type of friends request view 
     */
    private String currentLoggedInParticipant;
    private AcceptFriendRequestContract.AcceptFriendRequestView mAcceptFriendRequestView;
    private FirebaseFirestore db;

    /**
     * constructor
     * @param view the view of the contact page after friend request accepted
     * */
    public AcceptFriendRequestPresenter(AcceptFriendRequestContract.AcceptFriendRequestView view) {
        this.mAcceptFriendRequestView = view;
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

    }

    /**
     * setter
     * @param currentLoggedInParticipant the username of the current logged in participant
     * */
    @Override
    public void setCurrentLoggedInParticipant(String currentLoggedInParticipant) {
        this.currentLoggedInParticipant = currentLoggedInParticipant;

    }

    /**
     * This function retrieves all pending friend requests of current logged-in Participant from database
     * Result of the retrieval process will be passed through callback functions
     * defined in FriendshipOperationFeedback interface
     * */
    @Override
        public void retrievePendingFriendRequestOfAParticipant() {
            db.collection("PendingFriendRequests")
                    .whereEqualTo("friendToAdd", currentLoggedInParticipant)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<String> pendingFriendRequests = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    pendingFriendRequests.add(document.getString("username"));
                                }

                                retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(pendingFriendRequests);
                            }
                            else {
                                failRetrieveAllPendingFriendRequestsOfAParticipant(task.getException().getMessage());
                            }
                        }
                    });

        }


    /**
     * This function accepts a friend request sent to the current logged-in Participant.
     * Result of the acceptance process will be passed through callback functions
     * defined in FriendshipOperationFeedback interface
     * @param usernameOfFriendRequestToAccept the username of the Participant who created the friend request
     * */
    @Override
    public void acceptAFriendRequestOfAParticipant( final String usernameOfFriendRequestToAccept) {
        final CollectionReference itemsRef = db.collection("PendingFriendRequests");

        itemsRef
                .whereEqualTo("username", usernameOfFriendRequestToAccept)
                .whereEqualTo("friendToAdd", currentLoggedInParticipant)
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
                                                friendDocument.put("username" , currentLoggedInParticipant);
                                                friendDocument.put("friend", usernameOfFriendRequestToAccept);
                                                doc1.set(friendDocument);
                                                friendDocument.clear();
                                                friendDocument.put("username" , usernameOfFriendRequestToAccept);
                                                friendDocument.put("friend", currentLoggedInParticipant);
                                                doc2.set(friendDocument);
                                                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            acceptAFriendRequestOfAParticipantSuccessfully();
                                                        }else {
                                                           failAcceptAFriendRequestOfAParticipant(task.getException().getMessage());
                                                        }
                                                    }
                                                });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                failAcceptAFriendRequestOfAParticipant(e.getMessage());
                                            }
                                        });
                            }
                        }
                        else {
                            failAcceptAFriendRequestOfAParticipant(task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * this function retrieve all the pending friend request of a participant and
     * will set the data to adapter if successfully
     * @param pendingFriendRequests An array list of the name of pending friend requests
     * */
    private void retrieveAllPendingFriendRequestsOfAParticipantSuccessfully(ArrayList<String> pendingFriendRequests) {
        this.mAcceptFriendRequestView.setAdapterData(pendingFriendRequests);

    }

    /**
     * this function will display a error message if fail to retrieve pending friend request
     * @param message the error message that will be displayed if failed
     * */
    private void failRetrieveAllPendingFriendRequestsOfAParticipant(String message) {
        this.mAcceptFriendRequestView.displayErrorMessage(message);

    }

    /**
     * this function will display a success message
     * and change the data
     * */
    private void acceptAFriendRequestOfAParticipantSuccessfully() {
        this.mAcceptFriendRequestView.displaySuccessMessage("Friend Added");
        this.mAcceptFriendRequestView.notifyDatasetChanged();

    }

    /**
     * this function will display a error message
     * @param message  the error message that will display when failed
     * */
    private void failAcceptAFriendRequestOfAParticipant(String message) {
        this.mAcceptFriendRequestView.displayErrorMessage(message);

    }
}
