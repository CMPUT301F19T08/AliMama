package com.example.alimama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;



import com.example.alimama.FriendPageClickDelegate;
import com.example.alimama.FriendPageFragment;
import com.example.alimama.R;

/**
 * This class sets up RecyclerView for the FriendPageAdapter
 * */
public class FriendPageAdapter extends RecyclerView.Adapter<FriendPageAdapter.MyViewHolder> {
    ArrayList<String> contactList;
    Context context;
    FriendPageClickDelegate mClickDelegate;


    /**
     * Constructor for the contactList that would be displayed
     * in the fragment for each contact card
     * @param contactDataList
     * @param clickDelegate
     * */
    public FriendPageAdapter(ArrayList<String> contactDataList, FriendPageClickDelegate clickDelegate) {
        this.contactList = contactDataList;
        this.mClickDelegate = clickDelegate;


    }

    /**
     *
     * Provide reference for each item in view
     *
     * */
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView pic;
        TextView contactName;
        private Button addButton;



        public MyViewHolder(View itemView){
            super(itemView);
            pic = itemView.findViewById(R.id.imageV);
            contactName = itemView.findViewById(R.id.contact_name);
            addButton = itemView.findViewById(R.id.friend_add);


        }

        public Button getAddButton() {
            return addButton;
        }


    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_page_friend, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    /**
     * Replace the content of view when add/delete
     * @param position
     * initialize Accept button that would pass position to fragment
     * */
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String contact = contactList.get(position);
        holder.contactName.setText(contact);
        holder.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickDelegate.onFriendAddButtonClick(position);
            }
        });




    }

    /**
     *
     * This returns contactList size
     * @return contactList.size()
     * return the size of the ArrayList
     *
     */
    @Override
    public int getItemCount() {
        return contactList.size();
    }





}

