package com.example.alimama;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class MoodHistoryAdapter extends RecyclerView.Adapter<MoodHistoryViewHolder> {

    private MoodEventClickListener listener;
    private List<MoodEvent> moodEvents = new ArrayList<>();

    public MoodHistoryAdapter(MoodEventClickListener listener) {
        this.listener = listener;
    }

    void setMoodEvents(List<MoodEvent> moodEvents, String currentEmoticon) {
        this.moodEvents.clear();
        if (currentEmoticon.equals("Select filter")) {
            this.moodEvents.addAll(moodEvents);
        }
        for (int i = 0; i < moodEvents.size(); i++) {
            if (moodEvents.get(i).getEmoticon().equals(currentEmoticon)) {
                this.moodEvents.add(moodEvents.get(i));
            }
        }
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MoodHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mood_history, parent, false);
        return new MoodHistoryViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodHistoryViewHolder holder, int position) {
        holder.bind(moodEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return moodEvents.size();
    }
}
