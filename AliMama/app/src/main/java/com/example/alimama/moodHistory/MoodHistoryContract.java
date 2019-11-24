package com.example.alimama.moodHistory;

import com.example.alimama.Model.MoodEvent;

import java.util.ArrayList;

interface MoodHistoryContract {
    interface View {

        void selectMyHistoryButton();

        void selectFriendsHistoryButton();

        void showMoodEventsList(ArrayList<MoodEvent> moodEventHistory, String currentEmoticon, boolean disableDeleteButton);
    }

    interface Presenter {

        void onMoodItemSelected(String emoticon);

        void onMyHistoryClicked();

        void onFriendsHistoryButtonClicked();

        void onMoodHistoryViewResumed();

        void onMoodItemDeleteClicked(MoodEvent event);
    }
}
