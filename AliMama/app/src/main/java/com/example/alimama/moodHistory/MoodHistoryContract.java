package com.example.alimama.moodHistory;

import com.example.alimama.Model.MoodEvent;

import java.util.ArrayList;

/**
 * This is the interface of the Mood History Model that are called when each buttons are selected
 */

interface MoodHistoryContract {
    interface View {

        //listens for when the My History tab is selected and switches to that tab
        void selectMyHistoryButton();

        //listens for when the Friends History tab is selected and switches to that tab
        void selectFriendsHistoryButton();

        //used to retrieve all mood events of the current logged on user or the friends of the current logged on user
        void showMoodEventsList(ArrayList<MoodEvent> moodEventHistory, String currentEmoticon, boolean disableDeleteButton);
    }

    interface Presenter {

        //listens for what emoticon is selected in the emotional state spinner in addEditMood
        void onMoodItemSelected(String emoticon);

        //listens for when the My History tab is selected/clicked
        void onMyHistoryClicked();

        //listens for when the Friends History tab is selected/clicked
        void onFriendsHistoryButtonClicked();

        void onMoodHistoryViewResumed();

        //listens for when the delete button is clicked, the mood is deleted
        void onMoodItemDeleteClicked(MoodEvent event);
    }
}
