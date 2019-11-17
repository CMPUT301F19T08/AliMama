package com.example.alimama;

import com.example.alimama.Model.MoodEvent;

/**
 * This is the interface of the Mood Event Model that checks in the event that the edit button is pressed (to edit/view the selected mood) or the
 * delete button is pressed (to delete selected mood)
 */

interface MoodEventClickListener {
    void onEditClick(MoodEvent event);
    void onDeleteClick(MoodEvent event);
}
