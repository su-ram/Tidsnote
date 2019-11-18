package com.example.youngeun.parentapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;

public class MonthTab extends Fragment {

    private  Activity activity;
   private CalendarView calendarview;
   CommentDialog cd;
   private static final String TAG="MonthTab";
   private MainActivity mainActivity;
   public String choose_date;
   // public ArrayList<String> commentArrayList2;
   ArrayList<CommentRequest> commentRequestArrayList=new ArrayList<>();
   CommentDialog commentDialog;
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof Activity){
            activity=(Activity)context;
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.month_tab,container,false);
        calendarview=view.findViewById(R.id.calendarView);

      /* DisplayMetrics dm=view.getResources().getDisplayMetrics();
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        cd=new CommentDialog(activity);
        WindowManager.LayoutParams wm=cd.getWindow().getAttributes();
        wm.copyFrom(cd.getWindow().getAttributes());
        wm.width=width;
        wm.height=height;

*/


        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView v, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=year+"-"+month+"-"+dayOfMonth;
                dayOfMonth=dayOfMonth+1;
                String next_date=year+"-"+month+"-"+dayOfMonth;
                Log.d(TAG,date);



                CommentDialog commentDialog=new CommentDialog();
                commentDialog.setDate(date);
                commentDialog.setNextDate(next_date);
                commentDialog.show(getFragmentManager(),"Dialog");


                Toast.makeText(getContext(),"click"+date,Toast.LENGTH_LONG).show();
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
