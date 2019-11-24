package com.example.alimama.homescreen;

/**
 * This interface is the contract interface of HomeScreenActivity screen.
 * This interface is created to conform the Model-View-Presenter design pattern
 * No outstanding issues identified
 * @author Xuechun Hou
 * */
public interface HomeScreenContract {
    /**
     * This interface should be implemented by HomeScreenActivity screen presenter class
     * */
    interface HomeScreenPresenter {
        /**
         * this method will delegate view to switch the application back to participant login screen
         * */
        void logoutParticipant();
        /**
         * this method will delegate view to switch the application to view MoodHistory Screen
         * */
        void gotoViewMoodHistoryScreen();
        /**
         * this method will delegate view to switch the application to view MoodMap Screen
         * */
        void gotoViewMoodMapScreen();
        /**
         * this method will delegate view to switch the application to view or add friend Screen
         * */
        void gotoViewOrAddFriendScreen();
        /**
         * this method will set currentLoggedUser to the value passed in
         * @param currentLoggedInParticipant set currentLoggedInParticipant to newly passed value
         * */
        void setCurrentLoggedInParticipant(String currentLoggedInParticipant);



    }
    /**
     * This interface should be implemented by HomeScreenActivity screen activity class
     * */
    interface HomeScreenView {
        /**
         * this method will switch the application back to participant login screen
         * */
        void startParticipantLoginScreen();
        /**
         * this method will switch the application to view MoodHistory Screen
         * @param currentLoggedinParticipant participant information to be sent along intent
         * */
        void startViewMoodHistoryScreen(String currentLoggedinParticipant);


        /**
         * this method will switch the application to view or add friend Screen
         * @param currentLoggedinParticipant participant information to be sent along intent
         * */
        void startViewOrAddFriendScreen(String currentLoggedinParticipant);
        /**
         * this method will switch the application to view MoodMap Screen
         * @param currentLoggedinParticipant participant information to be sent along intent
         * */
        void startViewMoodMapScreen(String currentLoggedinParticipant);


        void displayCurrentLoggedInUser(String currentLoggedinParticipant);
    }
}
