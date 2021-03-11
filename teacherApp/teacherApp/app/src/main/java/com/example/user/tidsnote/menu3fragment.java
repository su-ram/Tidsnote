package com.example.user.tidsnote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static android.app.Activity.RESULT_OK;

public class menu3fragment extends Fragment {

    @Nullable
    Button chat_btn;
    private TabHost mtabHost;
    private ViewPager mviewpager;
    private Button feed,post;
    private TabLayout tabLayout;
    private View view;
    private ViewPager viewPager;
    ArrayList<postData> postDataArrayList;
    ArrayList<Notice> noticeArrayList,mylist;
    newsfeedTab newsfeedTab;
    chattingTab chattingTab;
    PostListAdapter postListAdapter;
    Context context;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_talk, container, false);

        viewPager=(ViewPager)view.findViewById(R.id.pager);

        List<Fragment> listFragments=new ArrayList<>();
        newsfeedTab = new newsfeedTab();
        context=this.getContext();

        listFragments.add(newsfeedTab);
        chattingTab = new chattingTab();

        listFragments.add(chattingTab);

        newsfeedTab.setPostDataArrayList(postDataArrayList);

        TabsPagerAdapter fragmentPagerAdapter=new TabsPagerAdapter(getChildFragmentManager(),listFragments);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout=(TabLayout)view.findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("게시판"));
        tabLayout.addTab(tabLayout.newTab().setText("내 글 관리"));
        //tabLayout.setTabTextColors(Color.parseColor("#7b664a"), Color.parseColor("#544733"));


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

        return view;
    }

    public void changeTab(){
        tabLayout.setSelected(true);
        tabLayout.selectTab(tabLayout.getTabAt(0));

    }
    @Override
    public void onResume() {
        super.onResume();

        if (getArguments()!=null &&getArguments().getBoolean("more",false)){

            getArguments().clear();
            changeTab();

        }


        if (chattingTab.adapter!=null)
            chattingTab.adapter.notifyItemChanged(((MainActivity)getActivity()).noticeList.size());
    }

    public PostListAdapter getAdapter(int mode){

        if (mode ==0){

            return newsfeedTab.getAdapter();
        }
        else
        {
            return chattingTab.getAdapter();
        }


    }
}
