package com.example.alimama.addEditMood;

import com.google.firebase.firestore.GeoPoint;

interface AddEditMoodContract {
    interface View {

        void setEditModeState();

        void setDate(String date);

        void setTime(String time);

        void setEmotionalState(String emotionalState);

        void setDescription(String description);

        void setThumbnail(String photoPath);

        void setEmoticon(String emoticon);

        void setSocialSituation(String socialSituation);

        void setLocation(GeoPoint geoPoint);

        void getCheckPremissionsAndGetPhoneLocation();

        void finish();

        void showDateError();

        void showTimeError();

        void showDescriptionError();

        void clearDateError();

        void clearTimeError();
    }
    interface Presenter {

        void onAddMoodButtonClicked(String dateStr, String timeStr, String emotionalState, String description, String emoticon, String socialSituation, boolean isCurrentLocationEnabled);

        void setLocation(GeoPoint geoPoint);

        void onGoogleMapReady();

        void setPhotoPath(String absolutePath);
    }
}
