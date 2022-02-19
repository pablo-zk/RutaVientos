package com.pablo_zuniga.rutavientos.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.pablo_zuniga.rutavientos.fragments.CreateRoutesFragment;
import com.pablo_zuniga.rutavientos.fragments.LoginFragment;
import com.pablo_zuniga.rutavientos.fragments.RoutesFragment;
import com.pablo_zuniga.rutavientos.fragments.UserFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numberOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                RoutesFragment routesFragment = new RoutesFragment();
                return routesFragment;
            case 1:
                CreateRoutesFragment createRoutesFragment = new CreateRoutesFragment();
                return createRoutesFragment;
            case 2:
                UserFragment userFragment = new UserFragment();
                return userFragment;
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }

}
