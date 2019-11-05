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
    private String userName;
    private View view;
    private RecyclerView recyclerView;
    private FriendPageAdapter friendPageAdapter;
    private Database db;


    private ArrayList<String> contactDataList;
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
        this.userName = "xhou2";
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FriendPage friendPage = (FriendPage) getContext();
        friendPageAdapter = new FriendPageAdapter(contactDataList, this );
        recyclerView.setAdapter(friendPageAdapter);
        db.retrieveAListOfParticipantsToAdd(this.userName, friendPage);

    }

    FriendPageAdapter getFriendPageAdapter() {
        return this.friendPageAdapter;
    }
    void setAdapterData(HashSet<String> updatedData) {
        this.contactDataList.clear();
        this.contactDataList.addAll(updatedData);
    }


    @Override
    public void onFriendAddButtonClick(int position) {
        String friendToAdd = contactDataList.get(position);
        contactDataList.remove(position);
        System.out.println(friendToAdd);
        this.db.sendFriendRequestFromCurrentParticipant(this.userName, friendToAdd,(FriendPage) getContext());

    }

    RecyclerView.Adapter getContactPageAdapter() {
        return this.friendPageAdapter;
    }
}
