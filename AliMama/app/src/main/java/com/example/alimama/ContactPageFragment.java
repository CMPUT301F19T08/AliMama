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
import java.util.HashSet;


import com.example.alimama.R;
import com.example.alimama.adapter.ContactPageAdapter;



public class ContactPageFragment extends Fragment {
    View view;

    private RecyclerView recyclerView;
    private ArrayList<String> contactDataList;
    private Database db;
    private String currParticipant;
    ContactPageAdapter contactPageAdapter;





    public ContactPageFragment() {
    }



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
        // hardcode for now will connect to main menu later
        this.currParticipant = "xhou9";


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FriendPage friendPage = (FriendPage)getContext();
        this.db.retrieveCurrentFriendsOfAParticipant(this.currParticipant, friendPage);
        this.db.registerCurrentFriendsOfAParticipantRealTimeListener(this.currParticipant, friendPage);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






    }


    public void setAdapterData(ArrayList<String> currentFriendsOfAParticipant) {
        this.contactDataList.clear();
        this.contactDataList.addAll(currentFriendsOfAParticipant);
    }

    public ContactPageAdapter getContactPageAdapter() {
        return this.contactPageAdapter;
    }
}
