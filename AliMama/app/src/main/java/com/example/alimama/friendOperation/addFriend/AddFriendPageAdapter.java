

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

public class AddFriendPageAdapter extends RecyclerView.Adapter<AddFriendPageAdapter.MyViewHolder> {
    ArrayList<String> contactList;

    FriendPageClickDelegate mClickDelegate;



    public AddFriendPageAdapter(ArrayList<String> contactDataList, FriendPageClickDelegate clickDelegate) {
        this.contactList = contactDataList;
        this.mClickDelegate = clickDelegate;


    }
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private ImageView pic;
        private TextView contactName;
        private Button addButton;



        public MyViewHolder(View itemView){
            super(itemView);
            pic = itemView.findViewById(R.id.imageV);
            contactName = itemView.findViewById(R.id.friend_name);
            addButton = itemView.findViewById(R.id.friend_add);



        }

        public Button getAddButton() {
            return addButton;
        }

        public void setAddButton(Button add) {
            this.addButton = add;
        }

        public TextView getContactName() {
            return contactName;
        }

        public void setContactName(TextView contactName) {
            this.contactName = contactName;
        }

        public ImageView getPic() {
            return pic;
        }

        public void setPic(ImageView pic) {
            this.pic = pic;
        }
    }



    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_page_friend, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contactList.size();
    }





}
