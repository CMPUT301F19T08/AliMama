package com.example.alimama.Model;

import com.google.firebase.firestore.DocumentId;
/**
 * UserProfile model class, which is an encapsulation credential of a Participant.
 * No outstanding issues identified
 * @author Xuechun Hou
 *
 *
 * */
public class UserProfile {

    @DocumentId
    private String documentId;
    private String username;
    private String password;


    /**
     * Class Constructor
     * */
    public UserProfile() {

    }
    /**
     * Class Constructor
     * @param username username of Participant
     * @param password password of Participant
     *
     * */
    public UserProfile(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * document id getter
     * @return documentId of UserProfile
     * */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * username getter
     * @return usenrame of UserProfile
     * */
    public String getUsername() {
        return username;
    }

    /**
     * password getter
     * @return password of UserProfile
     * */
    public String getPassword() {
        return password;
    }

    /**
     * documentId setter
     * @param documentId set documentId of the UserProfile to new value
     * */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * username setter
     * @param username set username of the UserProfile to new value
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * password setter
     * @param password set password of the UserProfile to new value
     * */
    public void setPassword(String password) {
        this.password = password;
    }
}
