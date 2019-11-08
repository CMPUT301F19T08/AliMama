package com.example.alimama;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.alimama.Controller.RequestPageAdapter;

import java.util.ArrayList;






/**
 * @author Zi Xuan Zhang
 * RequestPage tab's Fragment page.
 * Set up data for each contact card
 * For viewing.
 *
 * */

public class RequestPageFragment extends Fragment implements RequestPageClickDelegate{
    private DatabaseUtil db;
    View view;
    RecyclerView recyclerView;
    RequestPageAdapter requestPageAdapter;
    private String currParticipant;
    private ArrayList<String> contactDataList;

    /**
     * Constructor for fragment
     * @param currParticipant
     * */
    public RequestPageFragment(String currParticipant) {
        this.currParticipant = currParticipant;

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_recycler,container,false);


        recyclerView =view.findViewById(R.id.my_recycler_view);
        this.contactDataList = new ArrayList<>();
        requestPageAdapter = new RequestPageAdapter(contactDataList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.db = new DatabaseUtil();
        recyclerView.setAdapter(requestPageAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /*functionality*/
    /*receive all pending*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       db.retrievePendingFriendRequestOfAParticipant(this.currParticipant, (FriendPageActivity) getContext());

    }

    /**
     *  Get RequestPage adapter page adapter
     * @return RequestPageAdapter
     * */
    public RequestPageAdapter getRequestPageAdapter() {
        return this.requestPageAdapter;
    }

    /**
     * set Adapter for contactList.
     * add All of the friends to contactDataList
     * @param updatedData
     * */
    public void setAdapterData(ArrayList<String> updatedData ) {
        this.contactDataList.clear();
        this.contactDataList.addAll(updatedData);
    }




    /**
     * This is to initial the AcceptButton
     * @param position
     * */
    @Override
    public void onAcceptButtonClick(int position) {
        String friendToAdd = this.contactDataList.get(position);
        this.contactDataList.remove(position);
        db.acceptAFriendRequestOfAParticipant(friendToAdd, this.currParticipant, (FriendPageActivity) getContext());


    }
}
