package com.example.youngeun.parentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class MessageTab extends Fragment {

    FloatingActionButton add_btn;
    private Activity activity;
    ArrayList<messageData> messageDataArrayList;
    RecyclerView myRecyclerView;
    RecyclerViewAdapter rAdapter;
    FragmentActivity fragmentActivity;

    public RecyclerViewAdapter getAdapter(){
        return this.rAdapter;
    }

    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof Activity){
            activity=(Activity)context;
        }

    }

    public void setMessageDataArrayList(ArrayList<messageData> mdata){
        this.messageDataArrayList=mdata;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.message_tab, container, false);

        myRecyclerView=(RecyclerView)view.findViewById(R.id.myrecyclerview);
        messageDataArrayList=new ArrayList<messageData>();

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(layoutManager);
        this.rAdapter=new RecyclerViewAdapter(((MainActivity)getActivity()).messageList);
       ((MainActivity) getActivity()).recyclerViewAdapter=this.rAdapter;
        myRecyclerView.setAdapter(rAdapter);
        fragmentActivity=getActivity();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);

        messageDataArrayList= new ArrayList<>();
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==111){
                Toast.makeText(getContext(),"111",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final FloatingActionButton add_btn=activity.findViewById(R.id.add_btn);
        RecyclerView recyclerView=activity.findViewById(R.id.myrecyclerview);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,MessageActivity.class);
                intent.putExtra("message",new messageData());
                activity.startActivityForResult(intent,111);

            }
        });
    }
}