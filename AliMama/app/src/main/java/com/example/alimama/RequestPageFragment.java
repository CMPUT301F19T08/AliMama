package com.example.alimama;

import android.os.Bundle;
import android.util.Log;
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

import com.example.alimama.Database;
import com.example.alimama.TestFriendshipOperation;
import com.example.alimama.adapter.FriendPageAdapter;
import com.example.alimama.adapter.RequestPageAdapter;


import java.util.ArrayList;
import java.util.HashSet;

import com.example.alimama.R;


public class RequestPageFragment extends Fragment implements RequestPageClickDelegate{
    private Database db;
    FriendshipOperationFeedback friendshipOperationFeedback;

    View view;
    Button acceptButton;
    RecyclerView recyclerView;
    RequestPageAdapter requestPageAdapter;


    private String currParticipant;
    private ArrayList<String> contactDataList;
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


        this.db = new Database();

        recyclerView.setAdapter(requestPageAdapter);
        acceptButton = view.findViewById(R.id.friend_accept);


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
       db.retrievePendingFriendRequestOfAParticipant(this.currParticipant, (FriendPage) getContext());

    }

    public RequestPageAdapter getRequestPageAdapter() {
        return this.requestPageAdapter;
    }

    public void setAdapterData(ArrayList<String> updatedData ) {
        this.contactDataList.clear();
        this.contactDataList.addAll(updatedData);
    }




    @Override
    public void onAcceptButtonClick(int position) {
        String friendToAdd = this.contactDataList.get(position);
        this.contactDataList.remove(position);
        db.acceptAFriendRequestOfAParticipant(friendToAdd, this.currParticipant, (FriendPage) getContext());


    }
}
