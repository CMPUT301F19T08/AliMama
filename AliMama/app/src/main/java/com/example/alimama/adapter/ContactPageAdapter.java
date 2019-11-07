package com.example.alimama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


import com.example.alimama.R;

/**
 * This class sets up RecyclerView for the contactPageFragment
 * */
public class ContactPageAdapter extends RecyclerView.Adapter<ContactPageAdapter.MyViewHolder> {
    private ArrayList<String> contactList;
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    /**
     *
     * Provide reference for each item in view
     * @param
     * */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView pic;
        TextView contactName;
        CardView cv;

        public MyViewHolder(View itemView){
            super(itemView);
            pic = itemView.findViewById(R.id.imageV);
            contactName = itemView.findViewById(R.id.contact_name);
            cv = itemView.findViewById(R.id.card_view);
        }



    }



    /**
     * Constructor for the contactList that would be displayed
     * in the fragment for each contact card
     * @param contactDataList
     * */

    public ContactPageAdapter(ArrayList<String> contactDataList) {
        this.contactList = contactDataList;
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
                .inflate(R.layout.card_page_contact, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)

    /**
    * Replace the content of view when add/delete
    * */
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String contact = contactList.get(position);
        holder.contactName.setText(contact);


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

