package com.example.user.tidsnote;

import android.widget.TextView;

public class Timer extends Thread {

    private int count=0;
    private TextView textView;

    Timer(int count, TextView textView){
        count=count;
        textView=textView;
    }
    @Override
    public void run() {

        try{
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }





    }
}
