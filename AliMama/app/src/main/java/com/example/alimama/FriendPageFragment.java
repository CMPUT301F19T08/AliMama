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

import java.util.ArrayList;
import java.util.HashSet;

import com.example.alimama.R;
import com.example.alimama.TestFriendshipOperation;
import com.example.alimama.adapter.FriendPageAdapter;


public class FriendPageFragment extends Fragment {
    String userName;
    View view;
    RecyclerView recyclerView;
    FriendPageAdapter friendPageAdapter;
    Database db;
    Button acceptButton;

    private ArrayList<String> contactDataList;


    //public static final String[] contactList = {"PYTHON","JAVA","C","R","C#","SWIFT"};
    public FriendPageFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_recycler,container,false);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        this.contactDataList = new ArrayList<>();
        friendPageAdapter = new FriendPageAdapter(contactDataList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.db = new Database();

        recyclerView.setAdapter(friendPageAdapter);
        acceptButton = view.findViewById(R.id.friend_accept);


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








    }



    public void function (int position) {
        db.acceptAFriendRequestOfAParticipant("xhou2", this.contactDataList.get(position), (FriendPage) getContext());
        this.contactDataList.remove(position);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db.retrievePendingFriendRequestOfAParticipant("xhou2", (FriendPage) getContext());

    }

    public FriendPageAdapter getFriendPageAdapter() {
        return this.friendPageAdapter;
    }
    public void setAdapterData(ArrayList<String> updatedData ) {
        this.contactDataList.addAll(updatedData);
    }


}
