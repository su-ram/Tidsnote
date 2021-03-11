package com.example.user.tidsnote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class studentTab extends Fragment {

    private Activity activity;
    View view;
    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    FloatingActionButton fab;


    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


       view = inflater.inflate(R.layout.tab_student, container, false);
       fab=(FloatingActionButton)view.findViewById(R.id.addstd_btn);
       recyclerView=(RecyclerView)view.findViewById(R.id.menu4_student);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        studentAdapter = new StudentAdapter(((MainActivity)getActivity()).arrayList,1);
        ((MainActivity)getActivity()).addStudentAdapter=this.studentAdapter;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(studentAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Add_Student.class);
                intent.putExtra("student",new Student());
                getActivity().startActivityForResult(intent,2222);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}