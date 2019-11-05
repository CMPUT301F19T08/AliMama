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


public class FriendPageFragment extends Fragment implements ClickDelegate {
    String userName;
    View view;
    RecyclerView recyclerView;
    FriendPageAdapter friendPageAdapter;
    Database db;
    Button addButton;

    private ArrayList<String> contactDataList;


    //public static final String[] contactList = {"PYTHON","JAVA","C","R","C#","SWIFT"};
    public FriendPageFragment() {

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
        this.db = new Database();


        addButton = view.findViewById(R.id.friend_add);

        this.userName = "xhou2";
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








    }



//    public void function (int position) {
//        db.acceptAFriendRequestOfAParticipant("xhou2", this.contactDataList.get(position), (FriendPage) getContext());
//        this.contactDataList.remove(position);
//    }
//
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FriendPage friendPage = (FriendPage) getContext();
        friendPageAdapter = new FriendPageAdapter(contactDataList, this );
        recyclerView.setAdapter(friendPageAdapter);
        db.retrievePendingFriendRequestOfAParticipant(this.userName,friendPage);

    }

    public FriendPageAdapter getFriendPageAdapter() {
        return this.friendPageAdapter;
    }
    public void setAdapterData(ArrayList<String> updatedData ) {
        this.contactDataList.addAll(updatedData);
    }


    @Override
    public void onFriendAddButtonClick(int position) {
        String frindToAdd = contactDataList.get(position);
        contactDataList.remove(position);
        this.db.sendFriendRequestFromCurrentParticipant(this.userName, frindToAdd,(FriendPage) getContext() );

    }

    public RecyclerView.Adapter getContactPageAdapter() {
        return this.friendPageAdapter;
    }
}
