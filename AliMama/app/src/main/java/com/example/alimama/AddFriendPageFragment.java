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

import com.example.alimama.Controller.FriendPageAdapter;



/**
 * @author Zi Xuan Zhang
 * FriendPage tab's Fragment page.
 * Set up data for each contact card that can be added
 * For viewing.
 * Action: add friend
 * Function for later version: search friend
 * */
public class AddFriendPageFragment extends Fragment implements FriendPageClickDelegate {

    private View view;
    private RecyclerView recyclerView;
    private FriendPageAdapter friendPageAdapter;
    private DatabaseUtil db;
    private String currParticipant;
    private ArrayList<String> contactDataList;


    /**
     * Constructor for fragment
     * @param currParticipant
     *
     * */
    public AddFriendPageFragment(String currParticipant) {

        this.currParticipant = currParticipant;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_recycler,container,false);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        // list to be passed to Adapter
        this.contactDataList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.db = new DatabaseUtil();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FriendPageActivity friendPageActivity = (FriendPageActivity) getContext();
        friendPageAdapter = new FriendPageAdapter(contactDataList, this );
        recyclerView.setAdapter(friendPageAdapter);
        db.retrieveAListOfParticipantsToAdd(this.currParticipant, friendPageActivity);

    }

    /**
     *get ContactPageAdapter
     *
     * @return FriendPageAdapter
     * */

    FriendPageAdapter getFriendPageAdapter() {
        return this.friendPageAdapter;
    }

    /**
     * set Adapter for contactList.
     * add All of the friends to contactDataList
     * @param updatedData
     * */
    void setAdapterData(HashSet<String> updatedData) {
        this.contactDataList.clear();
        this.contactDataList.addAll(updatedData);
    }

    /**
     *This is a to initial add friend button
     *
     * @param position
     * */
    @Override
    public void onFriendAddButtonClick(int position) {
        String friendToAdd = contactDataList.get(position);
        contactDataList.remove(position);
        System.out.println(friendToAdd);
        this.db.sendFriendRequestFromCurrentParticipant(this.currParticipant, friendToAdd,(FriendPageActivity) getContext());

    }


    /**
     * Get FriendPage adapter page adapter
     *
     * @return FriendPageAdapter */
    RecyclerView.Adapter getContactPageAdapter() {
        return this.friendPageAdapter;
    }
}