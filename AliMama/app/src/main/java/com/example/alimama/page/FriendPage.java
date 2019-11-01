package com.example.alimama.page;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.alimama.R;
import com.example.alimama.fragment.ContactPageFragment;
import com.example.alimama.fragment.FriendPageFragment;
import com.example.alimama.fragment.RequestPageFragment;
import com.example.alimama.ui.main.TabPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class FriendPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ViewPager viewContact;
    private ViewPager viewFriend;
    private ViewPager viewRequest;

    private ViewPager viewPager;

    private TabPageAdapter tabPageAdapter;

    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tmp_tab_view);

        tabPageAdapter = new TabPageAdapter(this,getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        /*Fragments*/

        tabPageAdapter.addFragment(new ContactPageFragment(),"Contact Page");
        tabPageAdapter.addFragment(new FriendPageFragment(),"Friend Page");
        tabPageAdapter.addFragment(new RequestPageFragment(),"Request Page");

        viewPager.setAdapter(tabPageAdapter);



        tabLayout =findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);







         /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }*/


    }
}
