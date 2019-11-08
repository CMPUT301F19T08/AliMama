package com.example.alimama;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alimama.Model.MoodEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a class is the Mood History adapter for the recycler view to display on the MoodHistory activity
 */

class MoodHistoryAdapter extends RecyclerView.Adapter<MoodHistoryViewHolder> {

    private MoodEventClickListener listener;
    private List<MoodEvent> moodEvents = new ArrayList<>();

    /**
     * listener for the adapter
     * @param listener
     */
    public MoodHistoryAdapter(MoodEventClickListener listener) {
        this.listener = listener;
    }

    /**
     * Filters the Mood History screen based on what the emoticon that the user has picked
     * @param moodEvents
     * @param currentEmoticon
     */
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

    /**
     * creates the ViewHolder for the recycler view
     * @return  view
     * @return  listener
     */
    @NonNull
    @Override
    public MoodHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mood_history, parent, false);
        return new MoodHistoryViewHolder(view, listener);
    }

    /**
     * Binds the view holder to position to set and update views in the recycler view.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MoodHistoryViewHolder holder, int position) {
        holder.bind(moodEvents.get(position));
    }


    /**
     * creates the ViewHolder for the recycler view
     * @return  moodEvents.size() size of the mood events
     */
    @Override
    public int getItemCount() {
        return moodEvents.size();
    }
}
