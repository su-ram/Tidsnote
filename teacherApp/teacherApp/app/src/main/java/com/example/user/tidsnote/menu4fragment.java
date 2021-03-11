package com.example.user.tidsnote;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class menu4fragment extends Fragment {

    private Activity activity;
    ArrayList<Student> students;
    private ViewPager viewPager;
    private TabLayout settabLayout;
    Button addatd_btn;
    List<Fragment> slistFragments=new ArrayList<>();

    public void setClass_(Class_ class_){

        profileTab profileTab = new profileTab();
        profileTab.setClass_(class_);
        slistFragments.add(profileTab);
        slistFragments.add(new studentTab());

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        viewPager=(ViewPager)view.findViewById(R.id.set_pager);



        SettingTabsPagerAdapter fragmentPagerAdapter=new SettingTabsPagerAdapter(getChildFragmentManager(),slistFragments);

        viewPager.setAdapter(fragmentPagerAdapter);
        settabLayout=(TabLayout)view.findViewById(R.id.set_tablayout);
        settabLayout.addTab(settabLayout.newTab().setText("프로필설정"));
        settabLayout.addTab(settabLayout.newTab().setText("원아 관리"));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(settabLayout));

        settabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
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
    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final ImageButton add_btn=activity.findViewById(R.id.add_btn);
        final ImageButton profile_btn=getActivity().findViewById(R.id.profile_btn);
        final ImageButton list_btn=getActivity().findViewById(R.id.list_btn);


        profile_btn.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                final ImageButton profile_btn=getActivity().findViewById(R.id.profile_btn);
                Intent intent=new Intent(activity,classInfo.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(activity,addActivity.class);
                activity.startActivity(intent);

                activity.finish();
            }
        });
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,studentList.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });*/
}






