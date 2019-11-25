package com.example.alimama.friendOperation;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.ArrayList;


import com.example.alimama.R;


public class TabPageAdapter extends FragmentPagerAdapter {


    private final ArrayList<Fragment> setFragment = new ArrayList<>();
    private final ArrayList<String> setTitle = new ArrayList<>();


    public TabPageAdapter( FragmentManager fm) {
        super(fm);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//
        return setTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {

        return setFragment.get(position);
    }



    @Override
    public int getCount() {
        return setTitle.size();
    }


    public void addFragment(Fragment fragment,String title){
        setFragment.add(fragment);
        setTitle.add(title);
    }

}
