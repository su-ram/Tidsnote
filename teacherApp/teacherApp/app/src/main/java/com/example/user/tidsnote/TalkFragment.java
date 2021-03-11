package com.example.user.tidsnote;

import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class TalkFragment extends Fragment {


    @Nullable
    Button chat_btn;
    private TabHost mtabHost;
    private ViewPager mviewpager;
    private Button feed,post;
    private TabLayout tabLayout;
    private View view;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_talk, container, false);
        viewPager=(ViewPager)v.findViewById(R.id.pager);

        List<Fragment> listFragments=new ArrayList<>();
        listFragments.add(new newsfeedTab());
        listFragments.add(new chattingTab());

        TabsPagerAdapter fragmentPagerAdapter=new TabsPagerAdapter(getChildFragmentManager(),listFragments);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout=(TabLayout)v.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("게시판"));
        tabLayout.addTab(tabLayout.newTab().setText("내 글 관리"));
        tabLayout.setTabTextColors(Color.BLACK,Color.BLUE);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        viewPager.setCurrentItem(tab.getPosition());
                        break;
                    case 1:
                        viewPager.setCurrentItem(tab.getPosition());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }
}
