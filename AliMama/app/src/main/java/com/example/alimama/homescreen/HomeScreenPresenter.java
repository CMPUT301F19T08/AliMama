package com.example.alimama.homescreen;

/**
 * This class is the implementation of HomeScreenContract.HomeScreenPresenter interface
 * it's the presenter of Home screen.
 * It's to conform the Model-View-Presenter design Pattern
 * No outstanding issues identified
 * @author Xuechun Hou
 * */
public class HomeScreenPresenter implements HomeScreenContract.HomeScreenPresenter {

    private HomeScreenContract.HomeScreenView mHomeScreenView;
    private String currentLoggedInParticipant;
    /**
     * class constructor
     */
    public HomeScreenPresenter(HomeScreenContract.HomeScreenView view) {
        this.mHomeScreenView = view;

    }

    /**
     * this method will delegate view to switch the application back to participant login screen
     * */
    @Override
    public void logoutParticipant() {
        this.mHomeScreenView.startParticipantLoginScreen();

    }


    /**
     * this method will delegate view to switch the application to view MoodHistory Screen
     * */
    @Override
    public void gotoViewMoodHistoryScreen() {
        this.mHomeScreenView.startViewMoodHistoryScreen(currentLoggedInParticipant);


    }

    /**
     * this method will delegate view to switch the application to view MoodMap Screen
     * */
    @Override
    public void gotoViewMoodMapScreen() {
        this.mHomeScreenView.startViewMoodMapScreen(currentLoggedInParticipant);

    }


    /**
     * this method will delegate view to switch the application to view or add friend Screen
     * */
    @Override
    public void gotoViewOrAddFriendScreen() {
        this.mHomeScreenView.startViewOrAddFriendScreen(currentLoggedInParticipant);

    }

    /**
     * this method will set currentLoggedUser to the value passed in
     * @param currentLoggedinParticipant set currentLoggedInParticipant to newly passed value
     * */
    @Override
    public void setCurrentLoggedInParticipant(String currentLoggedinParticipant) {
        this.currentLoggedInParticipant = currentLoggedinParticipant;
    }
}
