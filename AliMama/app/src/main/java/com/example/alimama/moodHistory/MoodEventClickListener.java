package com.example.alimama.moodHistory;

import com.example.alimama.Model.MoodEvent;

/**
 * This is the interface of the Mood Event Model that checks in the event that the edit button is pressed (to edit/view the selected mood) or the
 * delete button is pressed (to delete selected mood)
 */

interface MoodEventClickListener {
    //listens for when the edit mood button is called in the MoodHistoryViewHolder
    void onEditClick(MoodEvent event);
    //listens for when the delete mood button is called in the MoodHistoryViewHolder
    void onDeleteClick(MoodEvent event);
}
