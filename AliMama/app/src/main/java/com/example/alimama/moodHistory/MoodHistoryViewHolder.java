package com.example.alimama.moodHistory;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alimama.Model.MoodEvent;
/**
 * This is a class is the Mood History view holder for the recycler view to display on the MoodHistory activity
 */

import com.example.alimama.R;
import com.example.alimama.moodHistory.MoodEventClickListener;


class MoodHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDate;
    private TextView tvTime;
    private TextView tvUsername;
    private TextView tvEmotion;
    private TextView tvEmoticon;
    private ImageButton btnEditMood;
    private ImageButton btnDeleteMood;

    private View view;
    private MoodEventClickListener listener;


    /**
     * items in the each view of the mood event in the Mood History screen
     * @param itemView
     * @param listener
     */

    public MoodHistoryViewHolder(@NonNull View itemView, final MoodEventClickListener listener) {
        super(itemView);
        this.listener = listener;
        tvDate = itemView.findViewById(R.id.tvDate);
        tvTime = itemView.findViewById(R.id.tvTime);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        tvEmotion = itemView.findViewById(R.id.tvEmotion);
        tvEmoticon = itemView.findViewById(R.id.tvEmoticon);

        btnEditMood = itemView.findViewById(R.id.btnEditMood);
        btnDeleteMood = itemView.findViewById(R.id.btnDeleteMood);
        this.view = itemView;

    }

    /**
     * Binds the mood event information to the view on the Mood History screen
     * @param moodEvent
     */
    public void bind(final MoodEvent moodEvent) {
        int year = moodEvent.getDate().getYear() + 1900;
        tvDate.setText(year + "-" + (moodEvent.getDate().getMonth() + 1) + "-" + moodEvent.getDate().getDate());
        tvTime.setText(moodEvent.getDate().getHours() + ":" + moodEvent.getDate().getMinutes());
        tvUsername.setText(moodEvent.getUsername());
        tvEmotion.setText(moodEvent.getEmotionalState());
        tvEmoticon.setText(moodEvent.getEmoticon());
        btnEditMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEditClick(moodEvent);
            }
        });

        btnDeleteMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(moodEvent);
            }
        });
        String color = moodEvent.getColor() == null ? "#FFFFFF" :  moodEvent.getColor();
        this.view.setBackgroundColor(Color.parseColor(color));
    }
}
