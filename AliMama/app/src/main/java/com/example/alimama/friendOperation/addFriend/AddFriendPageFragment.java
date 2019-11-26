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
 * FriendPage tab's Fragment page.
 * Set up data for each contact card that can be added
 * For viewing.
 * Action: add friend
 * Function for later version: search friend
 * @author Zi Xuan Zhang
 * */
public class AddFriendPageFragment extends Fragment implements FriendPageClickDelegate, AddFriendPageContract.AddFriendPageView {

    private AddFriendPageAdapter mAddFriendPageAdapter;
    private ArrayList<String> contactDataList;
    private AddFriendPagePresenter mAddFriendPagePresenter;


    /**
     * Constructor for fragment
     * @param currParticipant the name of current user
     * */

    public AddFriendPageFragment(String currParticipant) {

        this.mAddFriendPagePresenter = new AddFriendPagePresenter(this);
        this.mAddFriendPagePresenter.setCurrentLoggedInParticipant(currParticipant);

    }


    /**
     * Create the view
     * @param savedInstanceState  the bundle
     * @param inflater the layout inflater
     * @param container the view group
     * */

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

    /**
     * Create bundle
     * @param savedInstanceState the bundle
     * */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * Get ContactPageAdapter
     * @return AddFriendPageAdapter
     * */
    public AddFriendPageAdapter getAddFriendPageAdapter() {
        return this.mAddFriendPageAdapter;
    }


    /**
     * set Adapter for contactList.
     * add All of the friends to contactDataList
     * @param existingParticipants the list of existing friends
     * */
    @Override
    public void setAdapterData(HashSet<String> existingParticipants) {
        this.contactDataList.clear();
        this.contactDataList.addAll(existingParticipants);
        this.mAddFriendPageAdapter.notifyDataSetChanged();

    }

    /**
     * Display the error message
     * @param error the text of error message
     * */
    @Override
    public void displayErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

    }

    /**
     * Display the success message
     * @param successMessage the text of success message
     * */
    @Override
    public void displaySuccessMessage(String successMessage) {
        Toast.makeText(getContext(), successMessage, Toast.LENGTH_SHORT).show();

    }

    /**
     * Update the data set
     * */
    @Override
    public void notifyDatasetChaged() {
        this.mAddFriendPageAdapter.notifyDataSetChanged();
    }

    /**
     * Initial add friend button
     * @param position the position of click
     * */
    @Override
    public void onFriendAddButtonClick(int position) {
        String friendToAdd = contactDataList.get(position);
        contactDataList.remove(position);
        this.mAddFriendPagePresenter.sendFriendRequestFromCurrentParticipant(friendToAdd);

    }


    /**
     * Get FriendPage adapter page adapter
     * @return AddFriendPageAdapter
     * */
    public RecyclerView.Adapter getContactPageAdapter() {
        return this.mAddFriendPageAdapter;
    }
}