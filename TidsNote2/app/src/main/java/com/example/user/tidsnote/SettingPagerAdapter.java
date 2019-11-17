package com.example.user.tidsnote;


import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

public class SettingPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG="FragmentAdapter";
    private static final boolean DEBUG=false;

    //private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurtransaction=null;
    private Fragment mCurrentPrimaryItem=null;



    private final List<Fragment> sFragmentList=new ArrayList<>();

    public SettingPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                studentTab studenttab = new studentTab();
                return studenttab;
            case 1:
                profileTab profiletab = new profileTab();
                return profiletab;

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
        sFragmentList.add(fragment);
    }
}
