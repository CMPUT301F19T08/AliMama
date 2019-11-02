package com.example.alimama;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class EditMoodEvent extends AppCompatActivity {

    private ImageView addedPhoto;
    static final String happy = new String(Character.toChars(0x1F60A));
    static final String tears = new String(Character.toChars(0X1F602));
    static final String heart = new String(Character.toChars(0x1F60D));
    static final String angry = new String(Character.toChars(0x1F621));
    static final String tongue = new String(Character.toChars(0x1F61C));
    static final String cry = new String(Character.toChars(0x1F622));
    static final String smirk = new String(Character.toChars(0x1F60F));
    private static final String[] emoticon = {happy, tears, heart, angry, tongue, cry, smirk};
    private static final String[] social = {"Alone", "With one other person", "With two or several people", "With a crowd"};
    private EditText emotionalState;
    private EditText dateField;
    private EditText timeField;
    private EditText descriptionField;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    Button takePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);

        takePhotoButton = findViewById(R.id.take_photo_button);
        addedPhoto = findViewById(R.id.imageView);
        emotionalState = findViewById(R.id.emotional_state_field);
        dateField = findViewById(R.id.date_field);
        timeField = findViewById(R.id.time_field);
        descriptionField = findViewById(R.id.description_field);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(imageTakeIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);

                }
            }
        });

        Spinner emotionSpinner = findViewById(R.id.EmotionSpinner);
        ArrayAdapter<String> emotionAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, emoticon);
        emotionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emotionSpinner.setAdapter(emotionAdapter);

        Spinner socialSpinner = findViewById(R.id.SocialSpinner);
        ArrayAdapter<String> socialAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, social);
        socialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        socialSpinner.setAdapter(socialAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            addedPhoto.setImageBitmap(imageBitmap);
        }
    }
}