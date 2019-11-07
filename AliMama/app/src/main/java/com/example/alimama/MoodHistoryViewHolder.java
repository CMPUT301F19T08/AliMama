package com.example.alimama;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alimama.Model.MoodEvent;

class MoodHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDate;
    private TextView tvTime;
    private TextView tvUsername;
    private TextView tvEmotion;
    private ImageButton btnEditMood;
    private ImageButton btnDeleteMood;

    private MoodEventClickListener listener;


    public MoodHistoryViewHolder(@NonNull View itemView, final MoodEventClickListener listener) {
        super(itemView);
        this.listener = listener;
        tvDate = itemView.findViewById(R.id.tvDate);
        tvTime = itemView.findViewById(R.id.tvTime);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        tvEmotion = itemView.findViewById(R.id.tvEmotion);

        btnEditMood = itemView.findViewById(R.id.btnEditMood);
        btnDeleteMood = itemView.findViewById(R.id.btnDeleteMood);
    }

    public void bind(final MoodEvent moodEvent) {
        int year = moodEvent.getDate().getYear() + 1900;
        tvDate.setText(year + "-" +moodEvent.getDate().getMonth() + "-" + moodEvent.getDate().getDate());
        tvTime.setText(moodEvent.getDate().getHours() + ":" + moodEvent.getDate().getMinutes());
        tvUsername.setText(moodEvent.getUsername());
        tvEmotion.setText(moodEvent.getEmotionalState());

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
    }
}
