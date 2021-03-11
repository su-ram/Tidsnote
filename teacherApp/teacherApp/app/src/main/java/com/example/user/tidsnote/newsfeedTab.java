package com.example.user.tidsnote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognitionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class newsfeedTab extends Fragment {

    FloatingActionButton addPost_btn;
    private static final String TAG="NewsfeedTab";
    private Activity activity;
    RecyclerView postListView;
    postDBAdapter helper;
    Button post_btn;
    PostListAdapter adapter;
    ArrayList<postData> postDataArrayList;
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof Activity){
            activity=(Activity)context;
        }

    }

    public PostListAdapter getAdapter() {
        return adapter;
    }

    public void setPostDataArrayList(ArrayList<postData> postData){
        this.postDataArrayList=postData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){

        View view =inflater.inflate(R.layout.tab_newsfeed,container,false);
        helper = new postDBAdapter(view.getContext());

        postListView=(RecyclerView)view.findViewById(R.id.postList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                postListView.setLayoutManager(linearLayoutManager);
                adapter=new PostListAdapter(((MainActivity)getActivity()).noticeList);
                ((MainActivity)getActivity()).newsfeedAdapter=adapter;
                postListView.setAdapter(adapter);


        return view;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final Button post_btn=activity.findViewById(R.id.post_btn);



    }
}
