package com.example.user.tidsnote;

import androidx.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class SectionPagerAdapter extends FragmentPagerAdapter {


    private static final String TAG="FragmentAdapter";
    private static final boolean DEBUG=false;

    //private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurtransaction=null;
    private Fragment mCurrentPrimaryItem=null;



    private final List<Fragment> mFragmentList=new ArrayList<>();

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }


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
        }
    }

    private  static int PAGE_NUMBER=3;
    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }
    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
}
