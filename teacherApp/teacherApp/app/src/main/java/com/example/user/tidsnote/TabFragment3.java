package com.example.user.tidsnote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TabFragment3 extends Fragment {


    ArrayList<stt_msg> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.stt_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

/*
        for (int i=0; i<20; i++) {
            list.add(new stt_msg()) ;
        }
*/


        sttAdapter sttAdapter = new sttAdapter(list);
        recyclerView.setAdapter(sttAdapter);


        return view;
    }







}
