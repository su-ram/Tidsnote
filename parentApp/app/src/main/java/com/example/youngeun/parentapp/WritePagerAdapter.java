package com.example.youngeun.parentapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class WritePagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragments;

    public WritePagerAdapter(FragmentManager fm, List<Fragment> listFragments){
        super(fm);
        this.listFragments=listFragments;
    }

    @Override
    public Fragment getItem(int position){
        switch(position)
        {
            case 0:
                MessageTab messageTab=new MessageTab();
                return messageTab;
            case 1:
                RequestTab requestTab=new RequestTab();
                return requestTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){

        return listFragments.size();
    }

}
