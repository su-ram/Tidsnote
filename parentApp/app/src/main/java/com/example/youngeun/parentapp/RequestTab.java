package com.example.youngeun.parentapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class RequestTab extends Fragment {

    TextView p_id,s_name,c_name,t_name,t_pnum,s_pnum;
    EditText request;
    public ArrayList<String> infoArrayList;
    private Button logout;
    SharedPreferences pref;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.request_tab,container,false);
        p_id=view.findViewById(R.id.p_id);
        s_name=view.findViewById(R.id.s_name);
        c_name=view.findViewById(R.id.c_name);
        t_name=view.findViewById(R.id.t_name);
        t_pnum=view.findViewById(R.id.t_pnum);
        s_pnum=view.findViewById(R.id.s_pnum);
        logout=view.findViewById(R.id.logout);


        p_id.setText(myIDs.p_id);
        CommentRequest newInfo=new CommentRequest();
        newInfo.setS_id(myIDs.s_id);

        get_info("get_info",newInfo);

        s_name.setText(infoArrayList.get(0));
        s_pnum.setText(infoArrayList.get(1));
        t_name.setText(infoArrayList.get(2));
        t_pnum.setText(infoArrayList.get(3));
        c_name.setText(infoArrayList.get(4));




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pref=getActivity().getSharedPreferences("AutoLogin",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();

                editor.clear();
                editor.commit();
                Intent intent=new Intent(getActivity(),Login.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void get_info(String type, @Nullable Object obj) {


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
