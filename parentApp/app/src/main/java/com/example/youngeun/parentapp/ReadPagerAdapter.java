package com.example.youngeun.parentapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ReadPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragments;

    public ReadPagerAdapter(FragmentManager fm, List<Fragment> listFragments){
        super(fm);
        this.listFragments=listFragments;
    }

    @Override
    public Fragment getItem(int position){
        switch(position)
        {
            case 0:
                TodayTab todaytab=new TodayTab();
                return todaytab;
            case 1:
                MonthTab monthtab=new MonthTab();
                return monthtab;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){

        return listFragments.size();
    }

}
