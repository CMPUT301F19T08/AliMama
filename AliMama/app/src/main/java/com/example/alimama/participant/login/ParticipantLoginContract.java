package com.example.alimama.participant.login;

import com.example.alimama.Model.UserProfile;

/**
 * This interface is the contract interface of ParticipantLogin screen.
 * This interface is created to conform the Model-View-Presenter design pattern
 * No outstanding issues identified
 * @author Xuechun Hou
 * */
public interface ParticipantLoginContract {
    /**
     * This interface should be implemented by ParticipantLogin screen presenter class
     * */
    interface ParticipantLoginPresenter{

        /**
         * this method validate the credential of a new participant
         * @param username username of the new Participant
         * @param password password of the new Participant
         * */
        void verifyNewParticipantCredential(String username, String password);

        /**
         * this method validate the credential of an existing participant
         * @param username username of the existing Participant
         * @param password password of the existing Participant
         * */
        void verifyExistingParticipantCredential(String username, String password);

    }

    /**
     * This interface should be implemented by ParticipantLogin screen activity class
     * */
    interface ParticipantLoginView{


        /**
         * this method displays username empty error onto the Participant login screen
         * */
        void displayUsernameEmptyError();
        /**
         * this method displays password empty error onto the Participant login screen
         * */
        void displayPasswordEmptyError();
        /**
         * this method displays username password don't match error onto the Participant login screen
         * */
        void displayUsernamePasswordNotMatchError();
        /**
         * this method displays username already exist error onto the Participant login screen
         * */
        void displayUsernameAlreadyExistError();
        /**
         * this method displays username doesn't exist error onto the Participant login screen
         * */
        void displayUsernameDoNotExistError();
        /**
         * this method displays Toast onto the Participant login screen to indicate that existing participant log in successfully
         * */
        void displayExistingParticipantLoginSuccessToast();
        /**
         * this method displays Toast onto the Participant login screen to indicate that new participant sign up successfully
         * */
        void displayNewParticipantSignupSuccessToast();

        /**
         * this method displays Toast onto the Participant login screen to indicate that errors occur during new participant sign up
         * */
        void displayNewParticipantSignupDatabaseErrorToast(String message);
        /**
         * this method displays Toast onto the Participant login screen to indicate that errors occur during existing participant log in
         * */
        void displayExistingParticipantLoginDatabaseErrorToast(String message);

        /**
         * this method clears error displayed under username input field
         * */
        void clearUsernameError();
        /**
         * this method clears error displayed under password input field
         * */
        void clearPasswordError();
        /**
         * this method starts main menu screen and passes along information of current logged-in participant
         * */
        void startMainScreen(UserProfile currentLoggedInParticipant);
    }
}
