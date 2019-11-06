package com.example.alimama;

import com.example.alimama.Model.MoodEvent;

interface MoodEventClickListener {
    void onEditClick(MoodEvent event);
    void onDeleteClick(MoodEvent event);
}
