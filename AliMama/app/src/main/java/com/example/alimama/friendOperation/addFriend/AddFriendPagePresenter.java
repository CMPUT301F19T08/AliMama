package com.example.alimama.friendOperation.addFriend;

import com.example.alimama.friendOperation.addFriend.AddFriendPageContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AddFriendPagePresenter implements AddFriendPageContract.AddFriendPagePresenter {
    private String currentLoggedInParticipant;
    private AddFriendPageContract.AddFriendPageView mAddFriendPageView;
    private FirebaseFirestore db;
    public AddFriendPagePresenter(AddFriendPageContract.AddFriendPageView view) {
        this.mAddFriendPageView = view;
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

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
    @Override
    public void retrieveAListOfParticipantsToAdd( ) {
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
                            existingParticipants.remove(currentLoggedInParticipant);
                            CollectionReference friendsCollectionRef = db.collection("Friends");
                            friendsCollectionRef.whereEqualTo("username", currentLoggedInParticipant)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        failRetrieveAListOfParticipantsToAdd(e.getMessage());

                                    }else {
                                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                                            String currUsername = document.getString("friend");
                                            existingParticipants.remove(currUsername);
                                        }
                                        retrieveAListOfParticipantsToAddSuccessfully(existingParticipants);
                                    }

                                }
                            });



                        }
                        else  {
                            failRetrieveAListOfParticipantsToAdd(task.getException().getMessage());
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
    @Override
    public void sendFriendRequestFromCurrentParticipant(final String participantToBeSentFriendRequest) {
        CollectionReference pendingFriendRequestCollectionRef = db.collection("PendingFriendRequests");
        Map<String, String> pendingFriendRequestDocument = new HashMap<>();
        pendingFriendRequestDocument.put("username", currentLoggedInParticipant);
        pendingFriendRequestDocument.put("friendToAdd", participantToBeSentFriendRequest);
        pendingFriendRequestCollectionRef
                .add(pendingFriendRequestDocument)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {

                            sendFriendRequestFromCurrentParticipantSuccessfully();
                        }
                        else {
                            failSendFriendRequestFromCurrentParticipant(task.getException().getMessage());
                        }


                    }
                });
    }

    @Override
    public void setCurrentLoggedInParticipant(String currentLoggedInParticipant) {
        this.currentLoggedInParticipant = currentLoggedInParticipant;
    }




    private void retrieveAListOfParticipantsToAddSuccessfully(HashSet<String> existingParticipants) {
        this.mAddFriendPageView.setAdapterData(existingParticipants);

    }


    private void failRetrieveAListOfParticipantsToAdd(String message) {
        this.mAddFriendPageView.displayErrorMessage(message);

    }


    private void sendFriendRequestFromCurrentParticipantSuccessfully() {
        this.mAddFriendPageView.displaySuccessMessage("Friend Request Sent!");
        this.mAddFriendPageView.notifyDatasetChaged();

    }


    private void failSendFriendRequestFromCurrentParticipant(String message) {
        this.mAddFriendPageView.displayErrorMessage(message);

    }
}
