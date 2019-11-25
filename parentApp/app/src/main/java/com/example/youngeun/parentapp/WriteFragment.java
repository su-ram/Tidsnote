package com.example.youngeun.parentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;


public class WriteFragment extends Fragment {



    private TabLayout writetablayout;
    private ViewPager writepager;
    MessageTab messageTab;
    RequestTab requestTab;
    RecyclerViewAdapter adapter;
    ArrayList<messageData> messageDataArrayList;
    MenuInflater menuInflater;
    FragmentActivity fragmentActivity;
    Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_write, container, false);
        writepager=view.findViewById(R.id.writepager);
        List<Fragment> listFragments=new ArrayList<>();

        messageTab=new MessageTab();
        requestTab=new RequestTab();
        listFragments.add(messageTab);
        listFragments.add(requestTab);

        messageTab.setMessageDataArrayList(messageDataArrayList);
        WritePagerAdapter fragmentPagerAdapter=new WritePagerAdapter(getChildFragmentManager(),listFragments);
        writepager.setAdapter(fragmentPagerAdapter);
        writetablayout=view.findViewById(R.id.writetablayout);
        writetablayout.addTab(writetablayout.newTab().setText("코멘트"));
        writetablayout.addTab(writetablayout.newTab().setText("내 아이 정보"));


        writepager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(writetablayout));
        writetablayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener(){ @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch(tab.getPosition()){
                case 0:
                    writepager.setCurrentItem(tab.getPosition());

                    break;
                case 1:
                    writepager.setCurrentItem(tab.getPosition());
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
   @Override
   /*  public void onResume() {
        super.onResume();

        //Toast.makeText(getContext(),"onResume",Toast.LENGTH_SHORT).show();

        if (messageTab.recyclerViewAdapter!=null)
            messageTab.recyclerViewAdapter.notifyItemChanged(((MainActivity)getActivity()).messageList.size());
    }

    public RecyclerViewAdapter getAdapter() {
        return messageTab.getAdapter();
    }
*/
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.Messagebtn:
                Intent intent= new Intent(getActivity(),MessageActivity.class);
                startActivity(intent);
                break;

            default: return super.onOptionsItemSelected(item);
        }

        return true;

    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getContext(),"onResume",Toast.LENGTH_SHORT).show();

        if (messageTab.rAdapter!=null)
            messageTab.rAdapter.notifyItemChanged(((MainActivity)getActivity()).messageList.size());
    }

    public RecyclerViewAdapter getAdapter(){

            return messageTab.getAdapter();


    }



}
