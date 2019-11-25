package com.example.youngeun.parentapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class  ReadFragment extends Fragment {

    @Nullable
    public TabLayout tablayout;
    public myViewPager readpager;
    public View tabnot;
    public ArrayList<String> todaycommentList;
    public ArrayList<Bitmap> photolist;
    public TextView text;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_read,container,false);
        final View tabnot=inflater.inflate(R.layout.badge,null);
        readpager=(myViewPager)view.findViewById(R.id.readpager);

        List<Fragment> listFragments=new ArrayList<>();
        TodayTab todayTab = new TodayTab();
        todayTab.photolist = this.photolist;
        listFragments.add(todayTab);
        MonthTab monthTab = new MonthTab();
        listFragments.add(monthTab);


        ReadPagerAdapter fragmentPagerAdapter=new ReadPagerAdapter(getChildFragmentManager(),listFragments);
        readpager.setAdapter(fragmentPagerAdapter);
        tablayout=view.findViewById(R.id.readtablayout);


        tablayout.addTab(tablayout.newTab().setText("오늘의 소식"));
        tablayout.addTab(tablayout.newTab().setText("전체 소식"));



        CommentRequest newToday=new CommentRequest();
        newToday.setS_id(myIDs.s_id);
        newToday.setC_date("");

        view_Data("today_notification",newToday);


        tablayout.getTabAt(0).setCustomView(tabnot);
        if(todaycommentList.get(0).equals("0")){
            tabnot.findViewById(R.id.circle).setVisibility(View.INVISIBLE);
        }else{
            tabnot.findViewById(R.id.circle).setVisibility(View.VISIBLE);
        }

        text=tabnot.findViewById(R.id.text);
        text.setTextColor(Color.BLACK);
        readpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        readpager.setCurrentItem(0);
        tablayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        readpager.setCurrentItem(tab.getPosition());
                        tabnot.findViewById(R.id.circle).setVisibility(View.INVISIBLE);
                        text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 1:
                        readpager.setCurrentItem(tab.getPosition());
                        TextView text1=tabnot.findViewById(R.id.text);
                        text1.setTextColor(Color.BLACK);
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
    public void view_Data(String type, @Nullable Object obj) {


        Client client=new Client(this,type);
        client.setData(obj);
        client.start();
        try{
            client.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
