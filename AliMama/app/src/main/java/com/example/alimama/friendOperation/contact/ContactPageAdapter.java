
package com.example.alimama.friendOperation.contact;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


import com.example.alimama.R;

public class ContactPageAdapter extends RecyclerView.Adapter<ContactPageAdapter.MyViewHolder> {
    private ArrayList<String> contactList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView pic;
        private TextView contactName;


        MyViewHolder(View itemView){
            super(itemView);
            this.pic = itemView.findViewById(R.id.imageV);
            this.contactName = itemView.findViewById(R.id.contact_name);
        }


        public ImageView getPic() {
            return pic;
        }

        public void setPic(ImageView pic) {
            this.pic = pic;
        }

        public TextView getContactName() {
            return contactName;
        }

        public void setContactName(TextView contactName) {
            this.contactName = contactName;
        }
    }



    public ContactPageAdapter(ArrayList<String> contactDataList) {
        this.contactList = contactDataList;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_page_contact, parent, false);
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


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return contactList.size();
    }




}
