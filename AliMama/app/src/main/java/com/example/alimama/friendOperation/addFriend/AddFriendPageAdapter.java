package com.example.alimama.friendOperation.addFriend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


import com.example.alimama.R;

/**
 * This class works for adding friend
 * move to add friends page and click the add button
 * then enter the friends name to send request
 * */
public class AddFriendPageAdapter extends RecyclerView.Adapter<AddFriendPageAdapter.MyViewHolder> {
    ArrayList<String> contactList;
    FriendPageClickDelegate mClickDelegate;

    /**
     * Set the contact list and click delegate
     * @param contactDataList the array list of contact data
     * @param clickDelegate  the delegate of click
     * */
    public AddFriendPageAdapter(ArrayList<String> contactDataList, FriendPageClickDelegate clickDelegate) {
        this.contactList = contactDataList;
        this.mClickDelegate = clickDelegate;

    }

    /**
     * Manage the view of adding friend
     * */
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private ImageView pic;
        private TextView contactName;
        private Button addButton;

        /**
         * Create the view of find contact name and add button
         * @param itemView the view of add friend
         * */
        public MyViewHolder(View itemView){
            super(itemView);
            pic = itemView.findViewById(R.id.imageV);
            contactName = itemView.findViewById(R.id.friend_name);
            addButton = itemView.findViewById(R.id.friend_add);

        }

        /**
         * Getter
         * @return add button
         * */
        public Button getAddButton() {
            return addButton;
        }

        /**
         * Setter
         * @param add the add button
         * */
        public void setAddButton(Button add) {
            this.addButton = add;
        }

        /**
         * Getter
         * @return contact name
         * */
        public TextView getContactName() {
            return contactName;
        }

        /**
         * Setter
         * @param contactName the name of the contact
         * */
        public void setContactName(TextView contactName) {
            this.contactName = contactName;
        }

        /**
         * Getter
         * @return picture
         * */
        public ImageView getPic() {
            return pic;
        }

        /**
         * Setter
         * @param pic the picture
         * */
        public void setPic(ImageView pic) {
            this.pic = pic;
        }
    }


    /**
     * Create new views
     * */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_page_friend, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }


    /**
     * Replace the contents of a view
     * @param position the position of click
     * @param holder
     * */
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String contact = contactList.get(position);
        holder.getContactName().setText(contact);
        holder.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickDelegate.onFriendAddButtonClick(position);
            }
        });

    }

    /**
     * Return the size of your data set
     * @return the size of contact list
     * */
    @Override
    public int getItemCount() {
        return contactList.size();
    }





}