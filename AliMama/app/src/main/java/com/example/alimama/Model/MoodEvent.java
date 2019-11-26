package com.example.alimama.Model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

import androidx.annotation.NonNull;
/**
 * MoodEvent model class, which is an encapsulation all information related to Participant's mood.
 * No outstanding issues identified
* */

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
    private String color;
    /**
     * Class Constructor
     * */
    public MoodEvent() {
        this.date = new Date();
    }

    /**
     * Class Constructor
     * @param username username of the current logged-in participant.
     * @param emotionalState a string represents participant's emotional state.
     * */
    public MoodEvent(String username, String emotionalState) {
        this();
        this.emotionalState = emotionalState;
        this.username = username;
    }


    /**
     * Class Constructor
     * @param username username of the current logged-in participant.
     * @param emotionalState a string represents participant's emotional state.
     * @param socialSituation a string represents participant's social situation. could be one of:  alone, with one other person, with two to several people, or with a crowd.
     * @param reasonInText a string that briefly explains the reason of participant's current mood. Should be no more than 20 characters.
     *
     * */
    MoodEvent(String username, String emotionalState, String socialSituation, String reasonInText) {
        this();
        this.emotionalState = emotionalState;
        this.username = username;
        this.socialSituation = socialSituation;
        this.reasonInText = reasonInText;
    }


    /**
     * date getter
     * @return date of the MoodEvent
     * */
    public Date getDate() {
        return date;
    }

    /**
     * date setter
     * @param date set date of the MoodEvent to new value
     * */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * emotionalState getter
     * @return emotionalState of the MoodEvent
     * */
    public String getEmotionalState() {
        return emotionalState;
    }

    /**
     * emotionalState setter
     * @param emotionalState set emotionalState of the MoodEvent to new value
     * */
    public void setEmotionalState(String emotionalState) {
        this.emotionalState = emotionalState;
    }


    /**
     * reasonInText getter
     * @return reason of the MoodEvent
     * */
    public String getReasonInText() {
        return reasonInText;
    }

    /**
     * reasonInText setter
     * @param reasonInText set reasonInText of the MoodEvent to new value
     * */
    public void setReasonInText(String reasonInText) {
        this.reasonInText = reasonInText;
    }


    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }


    /**
     * socialSituation getter
     * @return socialSituation of the MoodEvent
     * */
    public String getSocialSituation() {
        return socialSituation;
    }


    /**
     * socialSituation setter
     * @param socialSituation set socialSituation of the MoodEvent to new value
     * */
    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }


    /**
     * locationOfMoodEvent getter
     * @return locationOfMoodEvent of the MoodEvent
    * */
    public GeoPoint getLocationOfMoodEvent() {
        return locationOfMoodEvent;
    }

    /**
     * locationOfMoodEvent setter
     * @param locationOfMoodEvent set locationOfMoodEvent of the MoodEvent to new value
     * */
    public void setLocationOfMoodEvent(GeoPoint locationOfMoodEvent) {
        this.locationOfMoodEvent = locationOfMoodEvent;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }


    /**
     * username getter
     * @return username of Participant that the MoodEvent belongs to
     * */
    public String getUsername() {
        return username;
    }

    /**
     * username setter
     * @param username set username of Participant who created the MoodEvent to new value
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return "Document ID: " + this.documentId + " Date: " + date.toString() +  " username: " + this.username + " emotionalState : " + emotionalState + " social Situation: " + socialSituation +"\n";
    }

    /**
     * emoticon getter
     * @return emoticon of the MoodEvent
     * */
    public String getEmoticon() {
        return emoticon;
    }

    /**
     * emoticon setter
     * @param emoticon set emoticon of the MoodEvent to new value
     * */

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }

    /**
     * color getter
     * @return color of the MoodEvent
     * */
    public String getColor() {
        return color;
    }

    /**
     * color setter
     * @param color set color of the MoodEvent to new value
     * */
    public void setColor(String color) {
        this.color = color;
    }
}
