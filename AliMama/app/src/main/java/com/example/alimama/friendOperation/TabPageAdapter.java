package com.example.alimama.friendOperation;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.ArrayList;



/**
 * Manage the adapter of tab page
 * */
public class TabPageAdapter extends FragmentPagerAdapter {


    private final ArrayList<Fragment> setFragment = new ArrayList<>();
    private final ArrayList<String> setTitle = new ArrayList<>();


    public TabPageAdapter( FragmentManager fm) {
        super(fm);

    }


    /**
     * Get page title
     * @param position the position of click
     * */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return setTitle.get(position);
    }

    /**
     * Get the fragment
     * @param position the position of click
     * */
    @Override
    public Fragment getItem(int position) {

        return setFragment.get(position);
    }

    /**
     * Get the count of titles
     * @return the size of title
     * */
    @Override
    public int getCount() {
        return setTitle.size();
    }

    /**
     * Add the fragment and title
     * @param fragment the fragment to add
     * @param title the name of the title
     * */
    public void addFragment(Fragment fragment,String title){
        setFragment.add(fragment);
        setTitle.add(title);
    }

}
