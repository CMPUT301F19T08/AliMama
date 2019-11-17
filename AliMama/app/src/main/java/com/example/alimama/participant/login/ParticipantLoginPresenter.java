package com.example.alimama.participant.login;

import com.example.alimama.Model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import androidx.annotation.NonNull;
/**
 * This class is the implementation of ParticipantLoginContract.ParticipantLoginPresenter interface
 * it's the presenter of Participant login screen.
 * It's to conform the Model-View-Presenter design Pattern
 * No outstanding issues identified
 * @author Xuechun Hou
 * */
public class ParticipantLoginPresenter implements ParticipantLoginContract.ParticipantLoginPresenter, CredentialValidationFeedback {

    private ParticipantLoginContract.ParticipantLoginView mParticipantLoginView;
    private FirebaseFirestore db;
    public ParticipantLoginPresenter(ParticipantLoginContract.ParticipantLoginView view ) {
        this.mParticipantLoginView = view;
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
    }


    /**
     * this method is the callback function when signing up new Participant but username already exist
     * */
    @Override
    public void usernameExist() {
        mParticipantLoginView.displayUsernameAlreadyExistError();

    }

    /**
     * this method is the callback function when logging in existing Participant successfully
     * */
    @Override
    public void existingParticipantLoginSuccessfully(UserProfile currentLoggedInParticipant) {
        mParticipantLoginView.displayExistingParticipantLoginSuccessToast();
        mParticipantLoginView.startMainScreen(currentLoggedInParticipant);


    }

    /**
     * this method is the callback function when signing up new Participant successfully
     * */
    @Override
    public void newParticipantSignupSuccessfully(UserProfile currentLoggedInParticipant) {
        mParticipantLoginView.displayNewParticipantSignupSuccessToast();
        mParticipantLoginView.startMainScreen(currentLoggedInParticipant);

    }

    /**
     * this method is the callback function when existing Participant entered wrong password
     * */
    @Override
    public void existingParticipantPasswordNotMatch() {
        mParticipantLoginView.displayUsernamePasswordNotMatchError();

    }

    /**
     * this method is the callback function when logging in existing Participant but username doesn't exist
     * */
    @Override
    public void usernameNotExist() {
        mParticipantLoginView.displayUsernameDoNotExistError();

    }

    /**
     * this method is the callback function when signing up new Participant but database error occurred
     * */
    @Override
    public void newParticipantSignupError(String message) {
        mParticipantLoginView.displayNewParticipantSignupDatabaseErrorToast(message);

    }

    /**
     * this method is the callback function when logging in existing Participant but database error occurred
     * */
    @Override
    public void existingParticipantLoginError(String message) {
        mParticipantLoginView.displayExistingParticipantLoginDatabaseErrorToast(message);


    }


    /**
     * this method validate the credential of a new participant
     * @param username username of the new Participant
     * @param password password of the new Participant
     * */
    @Override
    public void verifyNewParticipantCredential(String username, String password) {
        if (username.trim().length() == 0) {
            mParticipantLoginView.displayUsernameEmptyError();
            return;
        }
        mParticipantLoginView.clearUsernameError();
        if (password.trim().length() == 0) {
            mParticipantLoginView.displayPasswordEmptyError();
            return;
        }
        mParticipantLoginView.clearPasswordError();
        UserProfile newUser = new UserProfile(username.trim(),password.trim() );


        signupNewParticipant(newUser, this);

    }


    /**
     * this method validate the credential of an existing participant
     * @param username username of the existing Participant
     * @param password password of the existing Participant
     * */
    @Override
    public void verifyExistingParticipantCredential(String username, String password) {
        if (username.trim().length() == 0) {
            mParticipantLoginView.displayUsernamePasswordNotMatchError();
            return;
        }
        mParticipantLoginView.clearUsernameError();
        if (password.trim().length() == 0) {
            mParticipantLoginView.displayUsernamePasswordNotMatchError();
            return;
        }
        mParticipantLoginView.clearPasswordError();
        UserProfile existingUser = new UserProfile(username.trim(),password.trim() );

        authenticExistingParticipant(existingUser, this);
    }

    /**
     * Authenticate the given existing Participant.
     * Result of authentication will be passed through callback functions
     * defined in CredentialValidationFeedback interface
     * @param existingUser stores the credential of the existing Participant to be logged in
     * @param cvd a reference to an implementation of CredentialValidationFeedback interface
     *
     * */
    private void authenticExistingParticipant(final UserProfile existingUser, final CredentialValidationFeedback cvd) {



        // check if username exists
        DocumentReference usernameReference = db.collection("Participants").document(existingUser.getUsername());
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
                                UserProfile userProfile = document.toObject(UserProfile.class);
                                if (existingUser.getPassword().equals(userProfile.getPassword())) {
                                    cvd.existingParticipantLoginSuccessfully(existingUser);
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
     * defined in CredentialValidationFeedback interface
     * @param newUser stores the credential of the new Participant to be signed up

     * @param cvd a reference to an implementation of CredentialValidationFeedback interface
     *
     * */
    private void signupNewParticipant(final UserProfile newUser, final CredentialValidationFeedback cvd) {

        // check if username exists
        DocumentReference usernameReference = db.collection("Participants").document(newUser.getUsername());
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

                                db.collection("Participants").add(newUser)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    cvd.newParticipantSignupSuccessfully(newUser);
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


}
