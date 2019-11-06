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


public class FriendPageFragment extends Fragment implements FriendPageClickDelegate {

    private View view;
    private RecyclerView recyclerView;
    private FriendPageAdapter friendPageAdapter;
    private Database db;
    private String currParticipant;


    private ArrayList<String> contactDataList;
    public FriendPageFragment(String currParticipant) {
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
        this.db = new Database();

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
        this.db.sendFriendRequestFromCurrentParticipant(this.currParticipant, friendToAdd,(FriendPageActivity) getContext());

    }

    RecyclerView.Adapter getContactPageAdapter() {
        return this.friendPageAdapter;
    }
}
