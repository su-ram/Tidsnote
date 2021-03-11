package com.example.user.tidsnote;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private int tabCount=3;
    private Context context;


    public TabPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, int tabCount) {
        super(fm);
        this.fragments=fragmentList;
        this.tabCount=tabCount;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return tabCount;
    }
}
