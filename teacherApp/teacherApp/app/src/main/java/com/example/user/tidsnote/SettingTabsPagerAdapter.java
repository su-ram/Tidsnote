package com.example.user.tidsnote;


import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SettingTabsPagerAdapter extends FragmentPagerAdapter {

   List<Fragment> slistFragments;


    public SettingTabsPagerAdapter(FragmentManager fm , List<Fragment> listFragments){
        super(fm);
        this.slistFragments=listFragments;
    }

    @Override
    public Fragment getItem(int i) {

        return slistFragments.get(i);
    }

    @Override
    public int getCount() {
        return slistFragments.size();
    }
}
