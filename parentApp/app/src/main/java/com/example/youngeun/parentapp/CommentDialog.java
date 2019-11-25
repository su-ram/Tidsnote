package com.example.youngeun.parentapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommentDialog extends DialogFragment {


    public TextView ds_name,dc_date,dclass_name,dt_name,dm_menu,dc_feel,dc_meal,dc_health,dc_comment,dc_same,close_btn;;
    private static final String TAG="CommentDialog";
    public ArrayList<String> commentArrayList2;
    public ArrayList<Bitmap> photolist;
    private MonthTab monthTab=new MonthTab();
    ArrayList<CommentRequest> commentRequestArrayList=new ArrayList<>();
    String date,ndate;
    RecyclerView recyclerView;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle saveInstanceState){

        View view=inflater.inflate(R.layout.dialog_comment,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.photo_recycler);



        ds_name=(TextView)view.findViewById(R.id.ds_name);
        dc_date=(TextView)view.findViewById(R.id.dc_date);
        dclass_name=(TextView)view.findViewById(R.id.dclass_name);
        dt_name=(TextView)view.findViewById(R.id.dt_name);
        dc_feel=(TextView)view.findViewById(R.id.dc_feel);
        dc_meal=(TextView)view.findViewById(R.id.dc_meal);
        dc_health=(TextView)view.findViewById(R.id.dc_health);
        dc_comment=(TextView)view.findViewById(R.id.dc_comment);
        dc_same=(TextView)view.findViewById(R.id.dc_same);
        dm_menu=view.findViewById(R.id.dm_menu);
        close_btn=(TextView) view.findViewById(R.id.close_btn);


        CommentRequest newMonth=new CommentRequest();
        newMonth.setS_id(myIDs.s_id);
        newMonth.setC_date(date);
        newMonth.setN_date(ndate);

        commentRequestArrayList.add(newMonth);
        view_Data("choose_comment",newMonth);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new PhotoAdapter(photolist));


        dc_date.setText(date);
        ds_name.setText((CharSequence) commentArrayList2.get(0));
        dc_feel.setText((CharSequence) commentArrayList2.get(2));
        dc_meal.setText((CharSequence) commentArrayList2.get(3));
        dc_health.setText((CharSequence) commentArrayList2.get(4));
        dc_comment.setText((CharSequence) commentArrayList2.get(5));
        dc_same.setText((CharSequence) commentArrayList2.get(6));
        dt_name.setText(commentArrayList2.get(7)+"선생님");
       dclass_name.setText(commentArrayList2.get(8)+"반");
       dm_menu.setText(commentArrayList2.get(9));
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    public void setDate(String mdate){
        date=mdate;
    }
    public void setNextDate(String date){
        ndate=date;
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

