

package com.example.alimama.friendOperation.acceptFriendRequest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


import com.example.alimama.R;

/**
 * This class contains the request page and update the data
 * when sent a new friend request and
 * accepted or rejected the friend request
 * */
public class RequestPageAdapter extends RecyclerView.Adapter<RequestPageAdapter.MyViewHolder> {
    ArrayList<String> contactList;
    Context context;
    RequestPageClickDelegate rpc;

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * provide access to all the views for a data item in a view holder
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
            contactName = itemView.findViewById(R.id.request_name);
            cv = itemView.findViewById(R.id.request_card_view);
            acceptButton = itemView.findViewById(R.id.friend_accept);
        }



    }

    /**
     * Provide a suitable constructor depends on the kind of dataset
     * @param contactDataList the list of name of friends
     * @param rpc request page click delegate
     * */
    public RequestPageAdapter(ArrayList<String> contactDataList, RequestPageClickDelegate rpc) {
        this.contactList = contactDataList;
        this.rpc = rpc;
    }

    /**
     * Create new views (invoked by the layout manager)
     * @param parent the parent view
     * @param viewType the type of view
     * */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_page_request, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     * @param holder view holder
     * @param position the position of button click
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

            }
        });

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rpc.onAcceptButtonClick(position);

            }
        });
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     * @return he size of your dataset
     * */
    @Override
    public int getItemCount() {
        return contactList.size();
    }
}

