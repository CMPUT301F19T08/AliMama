package com.example.alimama;
/**
 * Should be implemented by classes that require Participant authentication part of the DatabaseUtil API
 * in order to receive return results from database
 * */
public interface CredentialValidationDelegate {
    void usernameExist();

    void existingParticipantLoginSuccessfully();
    void newParticipantSignupSuccessfully();

    void existingParticipantPasswordNotMatch();



    void usernameNotExist();

    void newParticipantSignupError(String message);
    void existingParticipantLoginError(String message);


}