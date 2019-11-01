package com.example.alimama.fragment;

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


import project.mood.R;
import project.mood.adapter.ContactPageAdapter;
import project.mood.model.Contact;


public class ContactPageFragment extends Fragment {
    View view;
    Contact contact;
    private RecyclerView recyclerView;
    private ArrayList<Contact> contactDataList;
    private String contractName;


    public static final String[] contactList = {"ALPHA","BETA","GAMMA","DELTA","THETA","OMEGA","ZETA"};

    public ContactPageFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.view_recycler,container,false);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        ContactPageAdapter contactPageAdapter = new ContactPageAdapter(contactDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(contactPageAdapter);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*contactDataList = new ArrayList<>();

        contactDataList.add(new Contact("ALPHA"));
        contactDataList.add(new Contact("BETA"));
        contactDataList.add(new Contact("OMEGA"));*/
        contactDataList = new ArrayList<>();
        for(int i=0;i<contactList.length;i++){

            contactDataList.add((new Contact(contactList[i])));


        }




    }
}
