package com.example.alimama.participant.login;

import com.example.alimama.Model.UserProfile;

/**
 * Should be implemented by classes that require Participant authentication part of the Database functionality
 * in order to receive return results from database
 * No outstanding issues identified
 * */
public interface CredentialValidationFeedback {
    /**
     * this method is the callback function when signing up new Participant but username already exist
     * */
    void usernameExist();
    /**
     * this method is the callback function when existing Participant entered wrong password
     * */
    void existingParticipantPasswordNotMatch();
    /**
     * this method is the callback function when logging in existing Participant but username doesn't exist
     * */
    void usernameNotExist();

    /**
     * this method is the callback function when signing up new Participant but database error occurred
     * */
    void newParticipantSignupError(String message);
    /**
     * this method is the callback function when logging in existing Participant but database error occurred
     * */
    void existingParticipantLoginError(String message);


    /**
     * this method is the callback function when signing up new Participant successfully
     * */
    void newParticipantSignupSuccessfully(UserProfile newUser);

    /**
     * this method is the callback function when logging in existing Participant successfully
     * */
    void existingParticipantLoginSuccessfully(UserProfile existingUser);
}