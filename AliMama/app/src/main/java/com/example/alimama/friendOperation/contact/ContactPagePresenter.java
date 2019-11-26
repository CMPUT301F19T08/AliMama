package com.example.alimama.friendOperation.contact;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Manage the current user's contact page
 * Update the data in fire store
 * */
public class ContactPagePresenter implements ContactPageContract.ContactPagePresenter {
    private String currentLoggedInParticipant;
    private ContactPageContract.ContactPageView mFriendOperationView;
    private FirebaseFirestore db;

    /**
     * Set friend operation view and fire store
     * @param view the contact page view
     * */
    public ContactPagePresenter(ContactPageContract.ContactPageView view) {
        this.mFriendOperationView = view;
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
    }

    /**
     * Set the current user
     * @param currentLoggedInParticipant the current user
     * */
    @Override
    public void setCurrentLoggedInParticipant(String currentLoggedInParticipant) {
        this.currentLoggedInParticipant = currentLoggedInParticipant;

    }

    /**
     * Set the data to adapter
     * @param currentFriendsOfAParticipant the list of friends of the current user
     * */
    private void retrieveCurrentFriendsOfAParticipantSuccessfully(ArrayList<String> currentFriendsOfAParticipant) {
        this.mFriendOperationView.setAdapterData(currentFriendsOfAParticipant);

    }

    /**
     * Display error message if failed
     * @param message the text of error message
     * */
    private void failRetrieveCurrentFriendsOfAParticipant(String message) {
        this.mFriendOperationView.displayExistingFriendsRetrievalErrorMessage(message);

    }

    /**
     * Registers a realtime listener of existing friend list of current logged-in Participant.
     * */
    @Override
    public void registerCurrentFriendsOfAParticipantRealTimeListener() {
        db.collection("Friends")
                .whereEqualTo("username",currentLoggedInParticipant)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {

                            failRetrieveCurrentFriendsOfAParticipant(e.getMessage());
                        }

                        ArrayList<String> existingFriends = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            existingFriends.add(doc.getString("friend"));

                        }
                        retrieveCurrentFriendsOfAParticipantSuccessfully(existingFriends);

                    }

                });
    }


    /**
     * Retrieves a list of existing friends of current logged-in Participant from database.
     * Result of the retrieval process will be passed through callback functions
     * defined in FriendshipOperationFeedback interface
     * */
    @Override
    public void retrieveCurrentFriendsOfAParticipant() {
        CollectionReference friendsCollectionReferece = db.collection("Friends");
        friendsCollectionReferece
                .whereEqualTo("username", currentLoggedInParticipant)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> friendsOfAParticipant = new ArrayList<>();
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                friendsOfAParticipant.add(document.getString("friend"));

                            }
                            retrieveCurrentFriendsOfAParticipantSuccessfully(friendsOfAParticipant);


                        }
                        else {
                            failRetrieveCurrentFriendsOfAParticipant(task.getException().getMessage());
                        }
                    }
                });
    }


























}
