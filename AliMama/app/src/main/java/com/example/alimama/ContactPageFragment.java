package com.example.alimama;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import com.example.alimama.Controller.ContactPageAdapter;


/**
 * @author Zi Xuan Zhang
 * ContactPage tab's Fragment page.
 * Set up data for each contact card
 * For viewing.
 *
 * */
public class ContactPageFragment extends Fragment {
    View view;

    private RecyclerView recyclerView;
    private ArrayList<String> contactDataList;
    private Database db;
    private String currParticipant;
    ContactPageAdapter contactPageAdapter;




    /**
     * Constructor for fragment
     * @param currParticipant
     *
     * */
    public ContactPageFragment(String currParticipant) {
        this.currParticipant = currParticipant;
    }



    /**
     * Set up view in fragment
     * */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.view_recycler,container,false);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        this.contactDataList = new ArrayList<>();
        this.contactPageAdapter = new ContactPageAdapter(contactDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(contactPageAdapter);
        this.db = new Database();
        // hardcode for now will connect to Controller menu later



        return view;
    }


    /**
     *Connect to FriendPage
     *
     * */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FriendPageActivity friendPageActivity = (FriendPageActivity)getContext();

        this.db.registerCurrentFriendsOfAParticipantRealTimeListener(this.currParticipant, friendPageActivity);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * set Adapter for contactList.
     * add All of the friends to contactDataList
     * @param currentFriendsOfAParticipant
     * */

    public void setAdapterData(ArrayList<String> currentFriendsOfAParticipant) {
        this.contactDataList.clear();
        this.contactDataList.addAll(currentFriendsOfAParticipant);
    }
    /**
     * Get contact page adapter
     * @return ContactPageAdapter
     * */

    public ContactPageAdapter getContactPageAdapter() {
        return this.contactPageAdapter;
    }
}
