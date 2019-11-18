package com.example.user.tidsnote;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragments;


    public TabsPagerAdapter(FragmentManager fm , List<Fragment> listFragments){
        super(fm);
        this.listFragments=listFragments;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                newsfeedTab newsfeedtab = new newsfeedTab();
                return newsfeedtab;
            case 1:
                chattingTab chattingtab = new chattingTab();
                return chattingtab;

            default:
                return null;
        }    }

    @Override
    public int getCount() {
        return listFragments.size();
    }
}
