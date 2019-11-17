package com.example.alimama.friendOperation.addFriend;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;

import com.example.alimama.R;


/**
 * @author Zi Xuan Zhang
 * FriendPage tab's Fragment page.
 * Set up data for each contact card that can be added
 * For viewing.
 * Action: add friend
 * Function for later version: search friend
 * */
public class AddFriendPageFragment extends Fragment implements FriendPageClickDelegate, AddFriendPageContract.AddFriendPageView {

    private AddFriendPageAdapter mAddFriendPageAdapter;
    private ArrayList<String> contactDataList;
    private AddFriendPagePresenter mAddFriendPagePresenter;


    /**
     * Constructor for fragment
     * @param currParticipant
     *
     * */
    public AddFriendPageFragment(String currParticipant) {

        this.mAddFriendPagePresenter = new AddFriendPagePresenter(this);
        this.mAddFriendPagePresenter.setCurrentLoggedInParticipant(currParticipant);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_recycler, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        // list to be passed to Adapter
        this.contactDataList = new ArrayList<>();
        mAddFriendPageAdapter = new AddFriendPageAdapter(contactDataList, this );
        recyclerView.setAdapter(mAddFriendPageAdapter);
        this.mAddFriendPagePresenter.retrieveAListOfParticipantsToAdd();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    /**
     *get ContactPageAdapter
     *
     * @return AddFriendPageAdapter
     * */

    AddFriendPageAdapter getAddFriendPageAdapter() {
        return this.mAddFriendPageAdapter;
    }



    /**
     * set Adapter for contactList.
     * add All of the friends to contactDataList
     * @param existingParticipants
     * */

    @Override
    public void setAdapterData(HashSet<String> existingParticipants) {
        this.contactDataList.clear();
        this.contactDataList.addAll(existingParticipants);
        this.mAddFriendPageAdapter.notifyDataSetChanged();

    }

    @Override
    public void displayErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void displaySuccessMessage(String successMessage) {
        Toast.makeText(getContext(), successMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void notifyDatasetChaged() {
        this.mAddFriendPageAdapter.notifyDataSetChanged();
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
        this.mAddFriendPagePresenter.sendFriendRequestFromCurrentParticipant(friendToAdd);

    }


    /**
     * Get FriendPage adapter page adapter
     *
     * @return AddFriendPageAdapter */
    RecyclerView.Adapter getContactPageAdapter() {
        return this.mAddFriendPageAdapter;
    }
}