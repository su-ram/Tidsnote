package com.example.youngeun.parentapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class TodayTab extends Fragment {


    TextView s_name,c_date,t_name,c_comment,class_name,c_same,c_feel,c_health,c_meal,m_menu;
    Button res_btn;
    RecyclerView recyclerView;
    private MainActivity mainActivity;
    public ArrayList<String> commentArrayList;
    ArrayList<CommentRequest> commentRequestArrayList=new ArrayList<>();
    ArrayList<Bitmap> photolist;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.today_tab, container, false);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        String curDate=s.format(new Date());

       // SharedPreferences ids=((MainActivity)mainActivity).context.getSharedPreferences("ids",Context.MODE_PRIVATE);
        //String s_id=ids.getString("s_id","");

        recyclerView = (RecyclerView)view.findViewById(R.id.photo_recycler);

        //Log.i("shared:",s_id);
        s_name=view.findViewById(R.id.s_name);
        c_date=view.findViewById(R.id.c_date);
        c_date.setText(curDate);
        t_name=view.findViewById(R.id.t_name);
        c_comment=view.findViewById(R.id.c_comment);
        c_same=view.findViewById(R.id.c_same);
        c_feel=view.findViewById(R.id.c_feel);
        c_meal=view.findViewById(R.id.c_meal);
        class_name=view.findViewById(R.id.class_name);
        c_health=view.findViewById(R.id.c_health);
        m_menu=view.findViewById(R.id.m_menu);

        CommentRequest newToday=new CommentRequest();
        newToday.setS_id(myIDs.s_id);
        newToday.setC_date("");

        commentRequestArrayList.add(newToday);
        view_Data("today_comment",newToday);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new PhotoAdapter(photolist));

        s_name.setText((CharSequence) commentArrayList.get(0));
        c_feel.setText((CharSequence) commentArrayList.get(2));

        c_meal.setText((CharSequence) commentArrayList.get(3));
        c_health.setText((CharSequence) commentArrayList.get(4));
        c_comment.setText((CharSequence) commentArrayList.get(5));
        c_same.setText((CharSequence) commentArrayList.get(6));
        t_name.setText(commentArrayList.get(7)+"선생님");
        class_name.setText(commentArrayList.get(8)+"반");
        m_menu.setText(commentArrayList.get(9));




      /*  res_btn=view.findViewById(R.id.res_btn);
        res_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                CommentRequest newToday=new CommentRequest();
                newToday.setS_id("student01");
                newToday.setC_date("");

                commentRequestArrayList.add(newToday);
                view_Data("today_comment",newToday);

                s_name.setText((CharSequence) commentArrayList.get(0));
                c_feel.setText((CharSequence) commentArrayList.get(2));

                c_meal.setText((CharSequence) commentArrayList.get(3));
                c_health.setText((CharSequence) commentArrayList.get(4));
                c_comment.setText((CharSequence) commentArrayList.get(5));
                c_same.setText((CharSequence) commentArrayList.get(6));
                t_name.setText(commentArrayList.get(7)+"선생님");
                class_name.setText(commentArrayList.get(8)+"반");


                Toast.makeText(getContext(),"server ok",Toast.LENGTH_LONG).show();

            }
        });
*/
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
