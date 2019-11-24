package com.example.alimama.friendOperation.contact;

import java.util.ArrayList;

public interface ContactPageContract {

    interface  ContactPagePresenter{
        void setCurrentLoggedInParticipant(String currentLoggedInParticipant);
        void registerCurrentFriendsOfAParticipantRealTimeListener();
        void retrieveCurrentFriendsOfAParticipant();


    }
    interface ContactPageView{
        void setAdapterData(ArrayList<String> currentFriendsOfAParticipant);
        void displayExistingFriendsRetrievalErrorMessage(String error);

    }
}
