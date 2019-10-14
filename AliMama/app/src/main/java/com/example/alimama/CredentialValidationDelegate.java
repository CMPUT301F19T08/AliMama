package com.example.alimama;

public interface CredentialValidationDelegate {
    void usernameExist();

    void existingParticipantLoginSuccessfully();
    void newParticipantSignupSuccessfully();

    void existingParticipantPasswordNotMatch();



    void usernameNotExist();

    void newParticipantSignupError(String message);
    void existingParticipantLoginError(String message);


}
