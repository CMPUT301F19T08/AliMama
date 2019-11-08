package com.example.alimama;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;

public class FriendPageActivityTest {


    CollectionReference mockCollectionReference = Mockito.mock(CollectionReference.class);
    DocumentReference mockDocumentReference = Mockito.mock(DocumentReference.class);
    Task<DocumentSnapshot> mockDocumentSnapshotTask = (Task<DocumentSnapshot>) Mockito.mock(Task.class);
    Task<Void> mockVoidTask = (Task<Void>) Mockito.mock(Task.class);
    OnCompleteListener<DocumentSnapshot> mockDocumentSnapshotCompleteListener = (OnCompleteListener<DocumentSnapshot>) Mockito.mock(OnCompleteListener.class);
    DocumentSnapshot mockDocumentSnapshot =  Mockito.mock(DocumentSnapshot.class);
    FriendshipOperationFeedback mockFriendshipOperationFeedback = Mockito.mock(FriendshipOperationFeedback.class);

    @Test
    public void testUserAcceptRequest(){
        String username = "username";
        String usernameOfFriendRequestToAccept= "friendToAdd";

        FirebaseFirestore firebaseFirestore = Mockito.mock(FirebaseFirestore.class);
        DatabaseUtil database = new DatabaseUtil(firebaseFirestore);

        Mockito.when(firebaseFirestore.collection("PendingFriendRequests"))
                .thenReturn(mockCollectionReference)
                .thenReturn((CollectionReference) mockCollectionReference
                .whereEqualTo("username",username)
                .whereEqualTo("friendToAdd",usernameOfFriendRequestToAccept)
                );
        Mockito.when(mockCollectionReference.document(username))
                .thenReturn(mockDocumentReference);
        Mockito.when(mockDocumentReference
                .get())
                .thenReturn(mockDocumentSnapshotTask);
        Mockito.when(mockDocumentSnapshotTask
                .addOnCompleteListener(any(OnCompleteListener.class)))
                .thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                OnCompleteListener<DocumentSnapshot> listener = (OnCompleteListener<DocumentSnapshot>) args[0];
                listener.onComplete(mockDocumentSnapshotTask);
                return null;
            }
        });

        Mockito.when(mockDocumentSnapshotTask.isSuccessful()).thenReturn(true);
        Mockito.when(mockDocumentSnapshotTask.getResult()).thenReturn(mockDocumentSnapshot);
        Mockito.when(mockDocumentSnapshot.exists()).thenReturn(true);
        database.acceptAFriendRequestOfAParticipant(username,usernameOfFriendRequestToAccept,mockFriendshipOperationFeedback);
        Mockito.verify(mockFriendshipOperationFeedback).acceptAFriendRequestOfAParticipantSuccessfully();
/*DocumentReference usernameReference = db.collection("Participants").document(username);
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
                });*/
    }



    @Test
    public void testUserToAdd() {
        String currentParticipant = "username";
        String participantToBeSentFriendRequest = "friendToAdd";

        Map<String, String> pendingFriendRequestDocument = new HashMap();
        pendingFriendRequestDocument.put("username", currentParticipant);
        pendingFriendRequestDocument.put("friendToAdd", participantToBeSentFriendRequest);

        FirebaseFirestore firebaseFirestore = Mockito.mock(FirebaseFirestore.class);
        DatabaseUtil database = new DatabaseUtil(firebaseFirestore);



        Mockito.when(firebaseFirestore.collection("PendingFriendRequests")
                .add(pendingFriendRequestDocument));

        Mockito.when(mockDocumentReference
                .get())
                .thenReturn(mockDocumentSnapshotTask);
        Mockito.when(mockDocumentSnapshotTask
                .addOnCompleteListener(any(OnCompleteListener.class)))
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        OnCompleteListener<DocumentSnapshot> listener = (OnCompleteListener<DocumentSnapshot>) args[0];
                        listener.onComplete(mockDocumentSnapshotTask);
                        return null;
                    }
                });

        Mockito.when(mockDocumentSnapshotTask.isSuccessful()).thenReturn(true);
        Mockito.when(mockDocumentSnapshotTask.getResult()).thenReturn(mockDocumentSnapshot);
        Mockito.when(mockDocumentSnapshot.exists()).thenReturn(true);
        database.sendFriendRequestFromCurrentParticipant(currentParticipant, participantToBeSentFriendRequest, mockFriendshipOperationFeedback);
        Mockito.verify(mockFriendshipOperationFeedback).sendFriendRequestFromCurrentParticipantSuccessfully();
    }


}
