package com.example.alimama;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddMoodEvent extends AppCompatActivity {

    private ImageView addedPhoto;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    Button takePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);

        takePhotoButton = findViewById(R.id.take_photo_button);
        addedPhoto = findViewById(R.id.imageView);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(imageTakeIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);

                }
            }
        });
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