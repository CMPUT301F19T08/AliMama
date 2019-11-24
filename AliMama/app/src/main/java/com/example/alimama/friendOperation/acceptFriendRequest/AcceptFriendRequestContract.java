package com.example.alimama.friendOperation.acceptFriendRequest;

import java.util.ArrayList;

public interface AcceptFriendRequestContract {
    interface AcceptFriendRequestPresenter{
        void setCurrentLoggedInParticipant(String currentLoggedInParticipant);
        void retrievePendingFriendRequestOfAParticipant();
        void acceptAFriendRequestOfAParticipant( final String usernameOfFriendRequestToAccept);
    }
    interface AcceptFriendRequestView{
        void displayErrorMessage(String error);
        void displaySuccessMessage(String successMessage);
        void setAdapterData(ArrayList<String> updatedData);

        void notifyDatasetChanged();
    }
}
