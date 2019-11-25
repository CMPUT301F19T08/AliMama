package com.example.alimama.friendOperation.addFriend;

import java.util.HashSet;

public interface AddFriendPageContract {

    interface AddFriendPagePresenter{
        void setCurrentLoggedInParticipant(String currentLoggedInParticipant);
        void retrieveAListOfParticipantsToAdd();
        void sendFriendRequestFromCurrentParticipant(final String participantToBeSentFriendRequest);

    }
    interface AddFriendPageView{
        void setAdapterData(HashSet<String> existingParticipants);
        void displayErrorMessage(String error);
        void displaySuccessMessage(String successMessage);

        void notifyDatasetChaged();
    }
}
