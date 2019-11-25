package com.example.alimama.friendOperation.acceptFriendRequest;

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


import com.example.alimama.R;

import java.util.ArrayList;
/**
 * RequestPage tab's Fragment page.
 * Set up data for each contact card
 * For viewing.
 * @author Zi Xuan Zhang
 * */
public class RequestPageFragment extends Fragment implements RequestPageClickDelegate, AcceptFriendRequestContract.AcceptFriendRequestView {

    private RequestPageAdapter requestPageAdapter;
    private ArrayList<String> contactDataList;
    private AcceptFriendRequestContract.AcceptFriendRequestPresenter mAcceptFriendRequestPresenter;

    /**
     * Constructor for fragment
     * @param currParticipant the name of current user
     * */
    public RequestPageFragment(String currParticipant) {
        this.mAcceptFriendRequestPresenter = new AcceptFriendRequestPresenter(this);
        this.mAcceptFriendRequestPresenter.setCurrentLoggedInParticipant(currParticipant);

    }

    /**
     * Create a view
     * @param container
     * @param inflater
     * @param savedInstanceState
     * @return view
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_recycler,container,false);


        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        this.contactDataList = new ArrayList<>();
        requestPageAdapter = new RequestPageAdapter(contactDataList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestPageAdapter);

        return view;
    }

    /**
     * Create view
     * @param savedInstanceState
     * */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /**
     * Functionality and receive all pending
     * @param savedInstanceState
     * */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mAcceptFriendRequestPresenter.retrievePendingFriendRequestOfAParticipant();

    }

    /**
     * Get RequestPage adapter page adapter
     * @return RequestPageAdapter
     * */
    public RequestPageAdapter getRequestPageAdapter() {
        return this.requestPageAdapter;
    }

    /**
     * Set Adapter for contactList.
     * add All of the friends to contactDataList
     * @param updatedData
     * */
    @Override
    public void setAdapterData(ArrayList<String> updatedData ) {
        this.contactDataList.clear();
        this.contactDataList.addAll(updatedData);
        this.requestPageAdapter.notifyDataSetChanged();
    }

    /**
     * Update data
     * */
    @Override
    public void notifyDatasetChanged() {
        this.requestPageAdapter.notifyDataSetChanged();
    }


    /**
     * This is to initial the AcceptButton
     * @param position
     * */
    @Override
    public void onAcceptButtonClick(int position) {
        String friendToAdd = this.contactDataList.get(position);
        this.contactDataList.remove(position);
        this.mAcceptFriendRequestPresenter.acceptAFriendRequestOfAParticipant(friendToAdd);


    }

    /**
     * Display the error message
     * @param error the text of error message
     * */
    @Override
    public void displayErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void displaySuccessMessage(String successMessage) {
        Toast.makeText(getContext(), successMessage, Toast.LENGTH_SHORT).show();

    }
}