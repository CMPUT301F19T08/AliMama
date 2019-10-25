package com.example.alimama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mainmenu extends AppCompatActivity {

    Button viewMoodHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        viewMoodHistoryButton=(Button)findViewById(R.id.mood_history_button);

        viewMoodHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMoodHistory = new Intent(Mainmenu.this, MoodHistory.class);
                startActivity(goToMoodHistory);
            }
        });


    }
}
