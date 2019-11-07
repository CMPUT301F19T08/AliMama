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


import com.example.alimama.R;

import com.example.alimama.RequestPageClickDelegate;
import com.example.alimama.RequestPageFragment;

public class RequestPageAdapter extends RecyclerView.Adapter<RequestPageAdapter.MyViewHolder> {
    ArrayList<String> contactList;
    Context context;
    RequestPageClickDelegate rpc;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    /**
     *
     * Provide reference for each item in view
     *
     * */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView pic;
        TextView contactName;
        CardView cv;
        Button acceptButton;


        public MyViewHolder(View itemView){
            super(itemView);
            pic = itemView.findViewById(R.id.imageV);
            contactName = itemView.findViewById(R.id.contact_name);
            cv = itemView.findViewById(R.id.card_view);
            acceptButton = itemView.findViewById(R.id.friend_accept);
        }



    }

    // Provide a suitable constructor (depends on the kind of dataset)
    /**
     * Constructor for the contactList that would be displayed
     * in the fragment for each contact card
     * @param contactDataList
     * @param rpc
     * */
    public RequestPageAdapter(ArrayList<String> contactDataList, RequestPageClickDelegate rpc) {
        this.contactList = contactDataList;
        this.rpc = rpc;
    }


    // Create new views (invoked by the layout manager)
    /**
     * Create new views
     *
     * */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_page_request, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }


    /**
     * Replacing the view when add/delete
     * @param position
     * initialize Accept button that would pass position to fragment
     * */

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String contact = contactList.get(position);
        holder.contactName.setText(contact);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"The position is:"+position,Toast.LENGTH_SHORT).show();
            }
        });

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"The button position is:"+position,Toast.LENGTH_SHORT).show();
                rpc.onAcceptButtonClick(position);



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

