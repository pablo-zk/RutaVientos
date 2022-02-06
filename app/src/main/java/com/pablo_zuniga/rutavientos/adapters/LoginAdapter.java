package com.pablo_zuniga.rutavientos.adapters;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pablo_zuniga.rutavientos.fragments.LoginFragment;
import com.pablo_zuniga.rutavientos.fragments.SignupFragment;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public LoginAdapter(@NonNull FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }
    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                SignupFragment signupFragment = new SignupFragment();
                return signupFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.totalTabs;
    }
}
