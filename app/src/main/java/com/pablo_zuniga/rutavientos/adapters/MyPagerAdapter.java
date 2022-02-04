package com.pablo_zuniga.rutavientos.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.pablo_zuniga.rutavientos.fragments.CreateRoutesFragment;
import com.pablo_zuniga.rutavientos.fragments.RoutesFragment;
import com.pablo_zuniga.rutavientos.fragments.UserFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numberOfTabs = behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new RoutesFragment();
            case 1:return new CreateRoutesFragment();
            case 2:return new UserFragment();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }

}
