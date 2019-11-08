package com.example.alimama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.alimama.Model.MoodEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * AddEditEvent Model class
 * It is the encapsulation of the information regarding to add/edit mood event.
 */
public class AddEditMoodActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, OnMapReadyCallback,
OnCompleteListener<Uri>, MoodEventManipulationFeedback{

    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int LOCATION_REQUEST_CODE = 1;

    private final int STATE_ADD = 0;
    private final int STATE_EDIT = 1;
    private int CURRENT_STATE = 0;


    static final String EXTRA_DOCUMENT_ID = "document_id";
    static final String EXTRA_USERNAME= "username";
    static final String EXTRA_DATE = "date";
    static final String EXTRA_TIME = "time";
    static final String EXTRA_EMOTIONAL_STATE = "emotional_state";
    static final String EXTRA_DESCRIPTION = "description";
    static final String EXTRA_PHOTO_PATH = "photo_path";
    static final String EXTRA_LOCATION_LAT = "location_lat";
    static final String EXTRA_LOCATION_LNG = "location_lng";
    static final String EXTRA_EMOTICON = "emoticon";
    static final String EXTRA_SOCIAL_SITUATION = "social_situation";





    private TextView tvTitle;
    private EditText etEmotionalState;
    private EditText etDatePicker;
    private EditText etTimePicker;
    private EditText etDescription;
    private ImageView ivThumbnail;
    private ImageButton ibCamera;
    private MapView mapView;
    private CheckBox checkBoxLocation;
    private Button btnAddMood;
    private Spinner spSocalSituation;
    private Spinner spEmoticon;

    private GoogleMap googleMap;
    private LatLng currentLocation;

    private Database database;
    private String currLoggedInUser;

    private MoodEvent event = new MoodEvent();
    String currentPhotoPath;
    Boolean didPhotoUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_mood);

        currLoggedInUser = getIntent().getStringExtra("USERNAME");
        tvTitle = findViewById(R.id.tvTitle);
        etEmotionalState = findViewById(R.id.etEmotionalState);
        etDatePicker = findViewById(R.id.etDate);
        etTimePicker = findViewById(R.id.etTime);
        etDescription = findViewById(R.id.etDescription);
        ivThumbnail = findViewById(R.id.ivThumbnail);
        ibCamera = findViewById(R.id.ibCamera);
        mapView = findViewById(R.id.mapView);
        checkBoxLocation = findViewById(R.id.checkBoxLocation);
        btnAddMood = findViewById(R.id.btnAddMood);
        spSocalSituation = findViewById(R.id.spSocialSituation);
        spEmoticon = findViewById(R.id.spEmoticon);

        database = new Database();
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        setupSocialSituationList();
        setupEmoticonsList();

        // These setup the text for the title and button
        String documentId = getIntent().getStringExtra(EXTRA_DOCUMENT_ID);
        if (documentId != null) {
            CURRENT_STATE = STATE_EDIT;
            tvTitle.setText("Edit Mood");
            btnAddMood.setText("Edit Mood");
            event.setDocumentId(documentId);
        }

        // Retrieve the user name information
        final String username = getIntent().getStringExtra(EXTRA_USERNAME);
        if (username != null) {
            event.setUsername(username);
        }

        // Retrieve the date information
        String dateStr = getIntent().getStringExtra(EXTRA_DATE);
        if (dateStr != null) {
            Date date = new Date(dateStr);
            int year = date.getYear() + 1900;
            etDatePicker.setText(year + "-" + date.getMonth() + "-" + date.getDate());
            etTimePicker.setText(date.getHours() + ":" + date.getMinutes());
            event.setDate(date);
        }

        // Retrieve the emotional state information
        String emotionalState = getIntent().getStringExtra(EXTRA_EMOTIONAL_STATE);
        if (emotionalState != null) {
            etEmotionalState.setText(emotionalState);
            event.setEmotionalState(emotionalState);
        }

        // Retrieve the description information
        String description = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        if (description != null) {
            etDescription.setText(description);
            event.setReasonInText(description);
        }

        // Retrieve the path of the photo information
        String photoPath = getIntent().getStringExtra(EXTRA_PHOTO_PATH);
        if (photoPath != null) {
            Glide.with(this).load(photoPath).into(ivThumbnail);
            event.setPathToPhoto(photoPath);
        }

        // Retrieve the emoticon information
        final String emoticon = getIntent().getStringExtra(EXTRA_EMOTICON);
        if (emoticon != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    spEmoticon.setSelection(getEmoticonPosition(emoticon));
                }
            });
            event.setEmoticon(emoticon);
        }

        // Retrieve the date information
        final String socialSituation = getIntent().getStringExtra(EXTRA_SOCIAL_SITUATION);
        if (socialSituation != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    spSocalSituation.setSelection(getSocialSituationPosition(socialSituation));
                }
            });
            event.setEmoticon(socialSituation);
        }

        // Let user to choose the date
        etDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        // Let user to choose the time
        etTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        // Let user to take a picture
        ibCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        // When the button at the bottom is clicked
        btnAddMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                event.setUsername(username);
                if (username == null) {
                    event.setUsername(currLoggedInUser);
                }
                event.setEmotionalState(etEmotionalState.getText().toString());
                event.setReasonInText(etDescription.getText().toString());
                event.setEmoticon(spEmoticon.getSelectedItem().toString());
                event.setSocialSituation(spSocalSituation.getSelectedItem().toString());

                Date eventDate = parseDate();
                event.setDate(eventDate);

                if (currentLocation != null && checkBoxLocation.isChecked())
                    event.setLocationOfMoodEvent(new GeoPoint(currentLocation.latitude, currentLocation.longitude));


                if (currentPhotoPath != null && didPhotoUpdate)
                    uploadFile(currentPhotoPath);
                else {
                    if (CURRENT_STATE == STATE_ADD)
                        database.addANewMoodEvent(event, AddEditMoodActivity.this);
                    else if (CURRENT_STATE == STATE_EDIT)
                        database.updateAnExistingMoodEvent(event, AddEditMoodActivity.this);
                }
            }
        });
    }

    /**
     * This function check the validation of date
     * @return the parsed date if it is valid
     */
    @Nullable
    private Date parseDate() {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
            date = dateFormat.parse(etDatePicker.getText().toString());

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm");
            Date time = timeFormat.parse(etTimePicker.getText().toString());

            date.setHours(time.getHours());
            date.setMinutes(time.getMinutes());

        } catch (ParseException exception) {

        }
        return date;
    }

    /**
     * This function set up the list for social situation spinner when editing
     */
    private void setupSocialSituationList() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.social_situation_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSocalSituation.setAdapter(adapter);
    }

    /**
     * This function setup the emoticon list when editing
     */
    private void setupEmoticonsList() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.emoticons_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmoticon.setAdapter(adapter);
    }

    /**
     * This function setup the picture when editing
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error creating photo file.", Toast.LENGTH_SHORT);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.alimama.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * This function retrieve the image
     * @return image Information of the picture
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * This function retrieve the result from the activity.
     * @param requestCode
     * It is the request code.
     * @param resultCode
     * It specifies by the second activity. This is either RESULT_OK if the operation was
     * successful or RESULT_CANCELED if the user backed out or the operation failed for some reason.
     * @param data
     * It carries the result data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            didPhotoUpdate = true;
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            ivThumbnail.setImageBitmap(imageBitmap);
        }
    }

    /**
     * This function shows the dialog that let user to choose a date
     * @param v Pass in the view
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * This function shows the dialog that let user to choose a time
     */
    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * This function set the date dialog when editing.
     * @param view It is the view of the current activity.
     * @param year The candidate year that user wants to edit.
     * @param month The candidate month that user wants to edit.
     * @param day The candidate day that user wants to edit.
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        etDatePicker.setText(String.format(Locale.CANADA, "%d-%d-%d", year, month + 1, day));
    }

    /**
     * This function set the date dialog when editing.
     * @param view It is the view of the current activity.
     * @param hourOfDay The candidate hour that users wants to edit.
     * @param minute The candidate minute that users wants to edit.
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        etTimePicker.setText(String.format(Locale.CANADA, "%d-%d", hourOfDay, minute));
    }

    /**
     * This function calls Gogle map.
     * @param googleMap This retrieve the Google map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        moveMapCameraToCurrentLocation(googleMap);
    }

    /**
     * This function locate the user location on Google Map.
     * @param googleMap The map that needs location.
     */
    private void moveMapCameraToCurrentLocation(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_REQUEST_CODE);
            return;
        }
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            currentLocation = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(currentLocation));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14.0f));
        }

        double locationLat = getIntent().getDoubleExtra(EXTRA_LOCATION_LAT, 0);
        double locationLng = getIntent().getDoubleExtra(EXTRA_LOCATION_LNG, 0);
        if (locationLat != 0 && locationLng != 0) {
            event.setLocationOfMoodEvent(new GeoPoint(locationLat, locationLng));
            currentLocation = new LatLng(locationLat, locationLng);
            googleMap.addMarker(new MarkerOptions().position(currentLocation));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14.0f));
        }
    }

    /**
     * This function get the result of the
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    moveMapCameraToCurrentLocation(googleMap);
                } else
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void uploadFile(String path) {
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
        }).addOnCompleteListener(this);
    }

    /**
     * Checks to see id the photo added is added or updated on the database.
     * @param task
     */
    @Override
    public void onComplete(@NonNull Task<Uri> task) {
        if (task.isSuccessful()) {
            Uri downloadUri = task.getResult();
            Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
            if (downloadUri != null) {
                event.setPathToPhoto(downloadUri.toString());
                if (CURRENT_STATE == STATE_ADD)
                    database.addANewMoodEvent(event, AddEditMoodActivity.this);
                else if (CURRENT_STATE == STATE_EDIT)
                    database.updateAnExistingMoodEvent(event, AddEditMoodActivity.this);
            }
        } else {
            Toast.makeText(this, "Photo upload failed", Toast.LENGTH_SHORT).show();
        }
    }

    // The following functions checks to see if updating or adding a mood event, or retrieving a mood event is successful or failed.
    // If failed them then an error message is printed
    @Override
    public void failToUpdateAnExistingMoodEvent(String errmsg) {

    }

    @Override
    public void updateAnExistingMoodEventSuccessfully() {
        finish();
    }

    @Override
    public void failToAddANewMoodEvent(String errmsg) {

    }

    @Override
    public void addANewMoodEventSuccessfully() {
        finish();
    }

    @Override
    public void retrieveAllMoodEventOfAParticipantSuccessfully(ArrayList<MoodEvent> moodEventHistory) {

    }

    @Override
    public void failToRetrieveAllMoodEventOfAParticipant(String errmsg) {

    }

    @Override
    public void deleteAMoodEventOfAParticipantSuccessfully() {

    }

    @Override
    public void failToDeleteAMoodEventOfAParticipant(String errmsg) {

    }

    @Override
    public void failRetrieveMostRecentMoodEventOfFriendsOfAParticipant(String message) {

    }

    @Override
    public void retrieveMostRecentMoodEventOfFriendsOfAParticipantSuccessfully(ArrayList<MoodEvent> mostRecentMoodEventsOfFriendsOfAParticipant) {

    }

    @Override
    public void failRegisterMoodEventRealTimeListener(String message) {

    }

    /**
     * Gets the emoticon selected of the mood using a switch statement
     * @param value
     */
    private int getEmoticonPosition(String value) {
        switch (value) {
            case "\uD83D\uDE0A":
                return 0;
            case "\uD83D\uDE02":
                return 1;
            case "\uD83D\uDE0D":
                return 2;
            case "\uD83D\uDE21":
                return 3;
            case "\uD83D\uDE1C":
                return 4;
            case "\uD83D\uDE22":
                return 5;
            case "\uD83D\uDE0F":
                return 6;
        }
        return -1;
    }

    /**
     * Gets the social situation of the mood using a switch statement
     * @param value
     */

    private int getSocialSituationPosition(String value) {
        switch (value) {
            case "alone":
                return 0;
            case "with one other person":
                return 1;
            case "with two to several ppl":
                return 2;
            case "with a crowd":
                return 3;
        }
        return -1;
    }
}
