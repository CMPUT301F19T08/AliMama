package com.example.alimama;

import com.example.alimama.Model.MoodEvent;

/**
 * This is the interface of the Mood Event Model.
 */
interface MoodEventClickListener {
    void onEditClick(MoodEvent event);
    void onDeleteClick(MoodEvent event);
}
