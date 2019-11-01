package com.example.alimama;



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


import project.mood.R;
import project.mood.model.Contact;

public class RequestPageAdapter extends RecyclerView.Adapter<RequestPageAdapter.MyViewHolder> {
    ArrayList<Contact> contactList;
    Context context;

    public RequestPageAdapter(ArrayList<Contact> contactDataList) {
        this.contactList = contactDataList;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public void requestAdapter(ArrayList<Contact> contactList) {
        this.contactList=contactList;
    }

    // Create new views (invoked by the layout manager)
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

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Contact contact = contactList.get(position);
        holder.contactName.setText(contact.getContactName());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"The position is:"+position,Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView pic;
        TextView contactName;
        CardView cv;
        Button accept;



        public ViewHolder(View itemView){
            super(itemView);
            pic = itemView.findViewById(R.id.imageV);
            contactName = itemView.findViewById(R.id.contact_name);
            cv = itemView.findViewById(R.id.card_view);
            accept = itemView.findViewById(R.id.friend_accept);

        }
    }


}

