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

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class ParticipantLoginSignupActivityTest {

    CollectionReference mockCollectionReference = Mockito.mock(CollectionReference.class);
    DocumentReference mockDocumentReference = Mockito.mock(DocumentReference.class);
    Task<DocumentSnapshot> mockDocumentSnapshotTask = (Task<DocumentSnapshot>) Mockito.mock(Task.class);
    Task<Void> mockVoidTask = (Task<Void>) Mockito.mock(Task.class);
    OnCompleteListener<DocumentSnapshot> mockDocumentSnapshotCompleteListener = (OnCompleteListener<DocumentSnapshot>) Mockito.mock(OnCompleteListener.class);
    DocumentSnapshot mockDocumentSnapshot =  Mockito.mock(DocumentSnapshot.class);
    CredentialValidationDelegate mockCredentialValidationDelegate = Mockito.mock(CredentialValidationDelegate.class);

    @Test
    public void testUsernameDoesNotExist() {

        FirebaseFirestore firebaseFirestore = Mockito.mock(FirebaseFirestore.class);
        DatabaseUtil database = new DatabaseUtil(firebaseFirestore);
        String username = "username";

        Mockito.when(firebaseFirestore.collection("Participants")).thenReturn(mockCollectionReference);
        Mockito.when(mockCollectionReference.document(username)).thenReturn(mockDocumentReference);
        Mockito.when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        Mockito.when(mockDocumentSnapshotTask.addOnCompleteListener(any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
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
        Mockito.when(mockDocumentSnapshot.exists()).thenReturn(false);

        database.authenticExistingParticipant(username, "", mockCredentialValidationDelegate);

        Mockito.verify(mockCredentialValidationDelegate).usernameNotExist();
    }

    @Test
    public void testUsernameExistsCorrectPassword() {

        FirebaseFirestore firebaseFirestore = Mockito.mock(FirebaseFirestore.class);
        DatabaseUtil database = new DatabaseUtil(firebaseFirestore);
        String username = "username";
        String password = "password";

        Mockito.when(firebaseFirestore.collection("Participants")).thenReturn(mockCollectionReference);
        Mockito.when(mockCollectionReference.document(username)).thenReturn(mockDocumentReference);
        Mockito.when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        Mockito.when(mockDocumentSnapshotTask.addOnCompleteListener(any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
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
        Mockito.when(mockDocumentSnapshot.get("password")).thenReturn(password);

        database.authenticExistingParticipant(username, password, mockCredentialValidationDelegate);

        Mockito.verify(mockCredentialValidationDelegate).existingParticipantLoginSuccessfully();
    }

    @Test
    public void testUsernameExistsIncorrectPassword() {

        FirebaseFirestore firebaseFirestore = Mockito.mock(FirebaseFirestore.class);
        DatabaseUtil database = new DatabaseUtil(firebaseFirestore);
        String username = "username";
        String password = "password";

        Mockito.when(firebaseFirestore.collection("Participants"))
                .thenReturn(mockCollectionReference);
        Mockito.when(mockCollectionReference.document(username)).thenReturn(mockDocumentReference);
        Mockito.when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        Mockito.when(mockDocumentSnapshotTask.addOnCompleteListener(any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
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
        Mockito.when(mockDocumentSnapshot.get("password")).thenReturn(password);

        database.authenticExistingParticipant(username, "incorrect", mockCredentialValidationDelegate);

        Mockito.verify(mockCredentialValidationDelegate).existingParticipantPasswordNotMatch();
    }

    @Test
    public void testSignUpUserWhichAlreadyExists() {

        FirebaseFirestore firebaseFirestore = Mockito.mock(FirebaseFirestore.class);
        DatabaseUtil database = new DatabaseUtil(firebaseFirestore);
        String username = "username";
        String password = "password";

        Mockito.when(firebaseFirestore.collection("Participants")).thenReturn(mockCollectionReference);
        Mockito.when(mockCollectionReference.document(username)).thenReturn(mockDocumentReference);
        Mockito.when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        Mockito.when(mockDocumentSnapshotTask.addOnCompleteListener(any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
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

        database.signupNewParticipant(username, password, mockCredentialValidationDelegate);

        Mockito.verify(mockCredentialValidationDelegate).usernameExist();
    }

    @Test
    public void testSignUpUserWhichDoesNotExist() {

        FirebaseFirestore firebaseFirestore = Mockito.mock(FirebaseFirestore.class);
        DatabaseUtil database = new DatabaseUtil(firebaseFirestore);
        String username = "username";
        String password = "password";

        Mockito.when(firebaseFirestore.collection("Participants")).thenReturn(mockCollectionReference);
        Mockito.when(mockCollectionReference.document(username)).thenReturn(mockDocumentReference);
        Mockito.when(mockDocumentReference.get()).thenReturn(mockDocumentSnapshotTask);
        Mockito.when(mockDocumentReference.set(any(Map.class))).thenReturn(mockVoidTask);
        Mockito.when(mockDocumentSnapshotTask.addOnCompleteListener(any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                OnCompleteListener<DocumentSnapshot> listener = (OnCompleteListener<DocumentSnapshot>) args[0];
                listener.onComplete(mockDocumentSnapshotTask);
                return null;
            }
        });

        Mockito.when(mockVoidTask.addOnCompleteListener(any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                OnCompleteListener<Void> listener = (OnCompleteListener<Void>) args[0];
                listener.onComplete(mockVoidTask);
                return null;
            }
        });
        Mockito.when(mockDocumentSnapshotTask.isSuccessful()).thenReturn(true);
        Mockito.when(mockVoidTask.isSuccessful()).thenReturn(true);
        Mockito.when(mockDocumentSnapshotTask.getResult()).thenReturn(mockDocumentSnapshot);
        Mockito.when(mockDocumentSnapshot.exists()).thenReturn(false);

        database.signupNewParticipant(username, password, mockCredentialValidationDelegate);

        Mockito.verify(mockCredentialValidationDelegate).newParticipantSignupSuccessfully();
    }

}