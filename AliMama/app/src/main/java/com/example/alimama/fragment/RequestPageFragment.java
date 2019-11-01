package com.example.alimama.fragment

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
import project.mood.adapter.RequestPageAdapter;
import project.mood.model.Contact;


public class RequestPageFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    RequestPageAdapter requestPageAdapter;
    private ArrayList<Contact> contactDataList;

    public static final String[] contactList = {"UBUNTU","UNIX","MACOS","WINDOWS","ANDROID","LINUX","DOS"};
    public RequestPageFragment() {

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_recycler,container,false);
        requestPageAdapter = new RequestPageAdapter(contactDataList);


        recyclerView =view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestPageAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        contactDataList = new ArrayList<>();
        for(int i=0;i<contactList.length;i++){

            contactDataList.add((new Contact(contactList[i])));
        }

    }
}
