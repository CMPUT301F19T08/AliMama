package com.example.alimama.addEditMood;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import io.grpc.Metadata;

import com.example.alimama.Model.MoodEvent;
import com.example.alimama.Model.MoodMetaData;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

/**
 * This is the interface that connects the database with the AddEditMood class
 */

public class AddEditMoodPresenter implements AddEditMoodContract.Presenter {
    private final int STATE_ADD = 0;
    private final int STATE_EDIT = 1;
    private int CURRENT_STATE = STATE_ADD;

    private AddEditMoodContract.View view;
    private MoodEvent event;

    private String photoPath = null;
    private GeoPoint location = null;


    private FirebaseFirestore db;

    /**
     * This function sets up the online database firebase.
     * @param view This is the view.
     */
    public AddEditMoodPresenter(AddEditMoodContract.View view) {
        this.view = view;

        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
    }


    /**
     * This function sets up the View for Add/Edit mood.
     * @param documentId This is the id of the document.
     * @param username This is the username that is loggged in.
     * @param dateStr This is the selected date.
     * @param emotionalState This is the selected emotion state.
     * @param description This is the description.
     * @param photoPath This is the path of the photo.
     * @param emoticon This is the selected emoticon.
     * @param socialSituation This is the selected social situation.
     * @param geoPoint This is the selected geoPoint of the location.
     */

    public void onAddEditMoodViewCreated(String documentId,
                                         String username,
                                         String dateStr,
                                         String emotionalState,
                                         String description,
                                         String photoPath,
                                         String emoticon,
                                         String socialSituation,
                                         GeoPoint geoPoint) {
        this.photoPath = photoPath;
        this.location = geoPoint;

        event = new MoodEvent();


        if (documentId != null) {
            CURRENT_STATE = STATE_EDIT;
            view.setEditModeState();
            event.setDocumentId(documentId);
        }

        if (username != null) {
            event.setUsername(username);
        }

        if (dateStr != null) {
            Date date = new Date(dateStr);
            int year = date.getYear() + 1900;
            int month = date.getMonth() + 1;
            int day = date.getDate();

            int hour = date.getHours();
            int minutes = date.getMinutes();

            view.setDate(year + "-" + month + "-" + day);
            view.setTime(hour + ":" + minutes);
            event.setDate(date);
        }

        if (emotionalState != null) {
            view.setEmotionalState(emotionalState);
            event.setEmotionalState(emotionalState);

        }

        if (description != null) {
            view.setDescription(description);
            event.setReasonInText(description);
        }

        if (photoPath != null) {
            view.setThumbnail(photoPath);
            event.setPathToPhoto(photoPath);
        }

        if (emoticon != null) {
            view.setEmoticon(emoticon);
            event.setEmoticon(emoticon);
            event.setColor(MoodMetaData.map.get(emoticon));
        }

        if (socialSituation != null) {
            view.setSocialSituation(socialSituation);
            event.setSocialSituation(socialSituation);
        }

        if (geoPoint != null && geoPoint.getLatitude() != 0 && geoPoint.getLongitude() != 0) {
            event.setLocationOfMoodEvent(geoPoint);
        }
    }


    /**
     * This function manages the resulf the and Add/Edit Mood button is clicked of the mood.
     * updates the mood event with the information that user has input and provides and error message when date, time is missing
     * or when the description is over 20 characters
     * @param dateStr This is the selected date of the mood.
     * @param timeStr This is the selected time of the mood.
     * @param emotionalState This is the selected emotional state of the mood.
     * @param description This is the description of the mood.
     * @param emoticon This is the selected emoticon of the mood.
     * @param socialSituation This is the selected social situation of the mood.
     * @param isCurrentLocationEnabled This is the
     */

    @Override
    public void onAddMoodButtonClicked(String dateStr, String timeStr, String emotionalState, String description, String emoticon, String socialSituation, boolean isCurrentLocationEnabled) {
        if (TextUtils.isEmpty(dateStr)) {
            view.showDateError();
            return;
        }
        view.clearDateError();
        if (TextUtils.isEmpty(timeStr)) {
            view.showTimeError();
            return;
        }

        view.clearTimeError();
        if (description != null && description.length() > 20) {
            view.showDescriptionError();
            return;
        }


        event.setDate(parseDate(dateStr, timeStr));
        event.setEmotionalState(emotionalState);
        event.setReasonInText(description);
        event.setEmoticon(emoticon);
        event.setSocialSituation(socialSituation);
        event.setColor(MoodMetaData.map.get(emoticon));

        if (location != null && isCurrentLocationEnabled) {
            event.setLocationOfMoodEvent(location);
        }

        if (photoPath != null && !photoPath.startsWith("http")) {
            uploadPhoto(photoPath);
        } else {
            if (CURRENT_STATE == STATE_ADD)
                addANewMoodEvent(event);
            else if (CURRENT_STATE == STATE_EDIT)
                updateAnExistingMoodEvent(event);

        }
    }


    /**
     * This function updates an MoodEvent of the current logged-in Participant. Result of the update process will be passed through callback functions
     * defined in MoodEventManipulationFeedback interface
     * @param moodEvent a MoodEvent object to be updated
     * @param mmf a reference to an implementation of MoodEventManipulationFeedback interface
     *
     *
     * */
    private void updateAnExistingMoodEvent(final MoodEvent moodEvent) {
        db.collection("MoodEvents")
                .document(moodEvent.getDocumentId())
                .set(moodEvent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateAnExistingMoodEventSuccessfully();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failToUpdateAnExistingMoodEvent(e.getMessage());
                    }
                });


    }


    /**
     * This function adds a new MoodEvent created by current logged-in Participant's to database.
     * Result of the add process will be passed through callback functions
     * defined in MoodEventManipulationFeedback interface
     * @param newMoodEvent a new MoodEvent object to be added
     * @param mmf a reference to an implementation of MoodEventManipulationFeedback interface
     *
     *
     * */
    private void addANewMoodEvent(final MoodEvent newMoodEvent) {

        db.collection("MoodEvents")
                .add(newMoodEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("successfully added a mood event");
                        addANewMoodEventSuccessfully();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getMessage());
                        failToAddANewMoodEvent(e.getMessage());

                    }
                });


    }

    /**
     * This function sets the new geeoPoint of the location.
     * @param geoPoint This is the geoPoint of the selected location.
     */
    @Override
    public void setLocation(GeoPoint geoPoint) {
        this.location = geoPoint;
        view.setLocation(geoPoint);
    }

    /**
     * This function set up the googleMap API.
     */
    @Override
    public void onGoogleMapReady() {
        view.setLocation(location);
        view.getCheckPremissionsAndGetPhoneLocation();
    }

    /**
     * This function sets up the absolute path of the photo.
     * @param absolutePath This is the absolute path.
     */
    @Override
    public void setPhotoPath(String absolutePath) {
        this.photoPath = absolutePath;
        view.setThumbnail(absolutePath);
    }

    /**
     * This function uploads a new picture.
     * @param path This is the path of the photo.
     */
    private void uploadPhoto(String path) {
        final Uri file = Uri.fromFile(new File(path));

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        UploadTask uploadTask = FirebaseStorage.getInstance().getReference().child("images/" + file.getLastPathSegment()).putFile(file, metadata);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return FirebaseStorage.getInstance().getReference().child("images/" + file.getLastPathSegment()).getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    if (downloadUri != null) {
                        event.setPathToPhoto(downloadUri.toString());
                        if (CURRENT_STATE == STATE_ADD)
                            addANewMoodEvent(event);
                        else if (CURRENT_STATE == STATE_EDIT)
                            updateAnExistingMoodEvent(event);
                    }
                }

            }
        });
    }

    /**
     * This function parse the date and the time from the input
     * @param dateStr This is the string of date.
     * @param timeStr This is the string of time.
     * @return The functions returns the converted date.
     */
    @Nullable
    private Date parseDate(String dateStr, String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
            date = dateFormat.parse(dateStr);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date time = timeFormat.parse(timeStr);

            date.setHours(time.getHours());
            date.setMinutes(time.getMinutes());

        } catch (ParseException exception) {

        }
        return date;
    }


    // the following are error messages for when a mood fails to add or update to the database
    private void failToUpdateAnExistingMoodEvent(String errmsg) {

    }


    private void updateAnExistingMoodEventSuccessfully() {
        view.finish();
    }


    private void failToAddANewMoodEvent(String errmsg) {

    }


    private void addANewMoodEventSuccessfully() {
        view.finish();
    }


}
