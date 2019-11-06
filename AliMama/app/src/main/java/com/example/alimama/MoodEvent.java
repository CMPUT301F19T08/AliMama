package com.example.alimama;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

import androidx.annotation.NonNull;

public class MoodEvent {
    @DocumentId
    private String documentId;
    private String username;
    private Date date;
    private String emotionalState;
    private String reasonInText;
    private String pathToPhoto;
    private String socialSituation;
    private String emoticon;
    private GeoPoint locationOfMoodEvent;
    MoodEvent() {
        this.date = new Date();
    }

    MoodEvent(String username, String emotionalState) {
        this();
        this.emotionalState = emotionalState;
        this.username = username;
    }

    MoodEvent(String username, String emotionalState, String socialSituation, String reasonInText) {
        this();
        this.emotionalState = emotionalState;
        this.username = username;
        this.socialSituation = socialSituation;
        this.reasonInText = reasonInText;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmotionalState() {
        return emotionalState;
    }

    public void setEmotionalState(String emotionalState) {
        this.emotionalState = emotionalState;
    }

    public String getReasonInText() {
        return reasonInText;
    }

    public void setReasonInText(String reasonInText) {
        this.reasonInText = reasonInText;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public GeoPoint getLocationOfMoodEvent() {
        return locationOfMoodEvent;
    }

    public void setLocationOfMoodEvent(GeoPoint locationOfMoodEvent) {
        this.locationOfMoodEvent = locationOfMoodEvent;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return "Document ID: " + this.documentId + " Date: " + date.toString() +  " username: " + this.username + " emotionalState : " + emotionalState + " social Situation: " + socialSituation +"\n";
    }

    public String getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }
}
