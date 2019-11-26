package com.example.alimama.friendOperation.contact;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


import com.example.alimama.R;

/**
 * Manage the contact page adapter
 * */
public class ContactPageAdapter extends RecyclerView.Adapter<ContactPageAdapter.MyViewHolder> {
    private ArrayList<String> contactList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     * */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView pic;
        private TextView contactName;


        MyViewHolder(View itemView){
            super(itemView);
            this.pic = itemView.findViewById(R.id.imageV);
            this.contactName = itemView.findViewById(R.id.contact_name);
        }

        /**
         * Get picture
         * @return picture
         * */
        public ImageView getPic() {
            return pic;
        }

        /**
         * Set picture
         * */
        public void setPic(ImageView pic) {
            this.pic = pic;
        }

        /**
         * Get contact name
         * @return contact name
         * */
        public TextView getContactName() {
            return contactName;
        }

        /**
         * Set contact name
         * */
        public void setContactName(TextView contactName) {
            this.contactName = contactName;
        }
    }


    /**
     * Set the contact page adapter
     * @param contactDataList the array list of contact data
     * */
    public ContactPageAdapter(ArrayList<String> contactDataList) {
        this.contactList = contactDataList;
    }

    /**
     * Create new views (invoked by the layout manager)
     * @param viewType the type of view
     * @param parent the last view
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_page_contact, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    /**
     * Replace the contents of a view
     * @param position the position of click
     * @param holder the holder of view
     * */
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String contact = contactList.get(position);
        holder.getContactName().setText(contact);


    }

    /**
     * Get the size of your data set
     * @return the size of contact list
     * */
    @Override
    public int getItemCount() {
        return contactList.size();
    }

}

