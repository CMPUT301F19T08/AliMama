package com.example.alimama.mapview;

/**
 * This interface is the contract interface of MoodMapActivity screen.
 * This interface is created to conform the Model-View-Presenter design pattern
 * No outstanding issues identified
 *
 * */
public interface MoodMapContract {


    /**
     * This interface should be implemented by MoodMapActivity screen presenter class
     * */
    interface MoodMapPresenter{
        /**
         * this method retrieves all located most recent MoodEvent of friends of a participant from database
         * */
        void retrieveAllLocatedMostRecentMoodEventsOfFriendsOfAParticipant();
        /**
         * this method retrieves all located MoodEvent of current logged-in participant from database
         * */
        void retrieveAllLocatedMoodEventsOfAParticipant();

        /**
         * this method will set currentLoggedUser to the value passed in
         * @param currentLoggedInParticipant set currentLoggedInParticipant to newly passed value
         * */
        void setCurrentLoggedInParticipant(String currentLoggedInParticipant);

    }
    /**
     * This interface should be implemented by MoodMapActivity screen activity class
     * */
    interface MoodMapView{

        /**
         * This method clears all marker currently displayed on GoogleMap
         * */
        void clearMap();
<<<<<<< HEAD

        /**
         * this method constructs a new GoogleMap Marker given parameters
         * @param username username to be displayed on the marker
         * @param latitude latitude to be displayed on the marker
         * @param longitude longitude to be displayed on the marker
         * @param emoticon emoticon to be displayed on the marker
         * */
        void createMarkerforUsers(String username, double latitude, double longitude, String emoticon);

        /**
         * this method constructs a new GoogleMap Marker given parameters
         * @param username username to be displayed on the marker
=======
        /**
         * this method constructs a new GoogleMap Marker given parameters
         * @param username username to be displayed on the marker
         * @param date date to be displayed on the marker
>>>>>>> 92c59567824ceb0fb5bdba72e36b14f9975cd474
         * @param latitude latitude to be displayed on the marker
         * @param longitude longitude to be displayed on the marker
         * @param emoticon emoticon to be displayed on the marker
         * */
<<<<<<< HEAD
        void createMarkerforFriends(String username, double latitude, double longitude, String emoticon);
=======
        void createMarker(String username, String date, double latitude, double longitude, String emoticon);
>>>>>>> 92c59567824ceb0fb5bdba72e36b14f9975cd474

        /**
         * this method displays a Toast showing the error message
         * @param error the error message
         * */
        void displayLocatedMoodEventDatabaseErrorToast(String error);

    }
}
