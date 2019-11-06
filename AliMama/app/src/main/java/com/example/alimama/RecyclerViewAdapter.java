package com.example.alimama;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MoodEvent> list;
    Context context;

    public RecyclerViewAdapter(ArrayList<MoodEvent> list, Context context) {
        this.list = list;
        this.context = context;
    }

//
//    private final ClickDelegate mClickDelegate;
//
//    RecyclerViewAdapter(ArrayList<MoodEvent> list, ClickDelegate clickDelegate) {
//        this.list = list;
//
//        this.mClickDelegate = clickDelegate;
//
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new MoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MoodListViewHolder moodListViewHolder = (MoodListViewHolder) holder;
        moodListViewHolder.getEmotionalState().setText(this.list.get(position).getEmotionalState());
        moodListViewHolder.getDate().setText((CharSequence) this.list.get(position).getDate());
        moodListViewHolder.getTime().setText(this.list.get(position).getTime());
        //moodListViewHolder.getUsername().setText(this.list.get(position).getUsername());
        moodListViewHolder.getReasonInText().setText(this.list.get(position).getReasonInText());


//        ((MoodListViewHolder) holder).getDeleteMoodBtn().setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                mClickDelegate.onDeleteMoodBtnClick(position);
//
//            }
//        });
//
//
//        ((MoodListViewHolder) holder).getUpdateMoodBtn().setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                mClickDelegate.onUpdateMoodBtnClick(position);
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, MoodEvent moodEvent) {
        list.add(position, moodEvent);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(MoodEvent moodEvent) {
        int position = list.indexOf(moodEvent);
        list.remove(position);
        notifyItemRemoved(position);
    }

    class MoodListViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateValue;
        private final TextView timeValue;
        private final TextView emotionalStateValue;
        //private final TextView username;
        private final TextView reason;
        //private final ImageButton updateMoodBtn;
        //private final ImageButton deleteMoodBtn;

        public MoodListViewHolder(View itemView) {
            super(itemView);
            dateValue = itemView.findViewById(R.id.listitem_date_value);
            timeValue = itemView.findViewById(R.id.listitem_time_value);
            emotionalStateValue = itemView.findViewById(R.id.listitem_emotional_state);
            //username = itemView.findViewById(R.id.username_value);
            reason = itemView.findViewById(R.id.reason_value);
            //updateMoodBtn = itemView.findViewById(R.id.listitem_edit_mood_btn);
            //deleteMoodBtn = itemView.findViewById(R.id.listitem_delete_mood_btn);
        }

        public TextView getDate() {
            return dateValue;
        }

        public TextView getTime() {
            return timeValue;
        }

        public TextView getEmotionalState() {
            return emotionalStateValue;
        }

        public TextView getReasonInText() {
            return reason;

        }

//        public TextView getUsername() {
//            return username;
//        }

//        ImageButton getUpdateMoodBtn() {
//            return updateMoodBtn;
//        }
//
//        ImageButton getDeleteMoodBtn() {
//            return deleteMoodBtn;
//        }
    }
}