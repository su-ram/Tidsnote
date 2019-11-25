package com.example.user.tidsnote;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Emergency {

    //키워드 긴급
    //1. 119신고
    //2. 학부모 push 알림
    //3. 선생님 push 알림

    public static String keyword,t_id;
    public static Student student;
    Context context;
    Activity activity;


    Emergency(Student student, String t_id){
        this.student = student;
        this.t_id = t_id;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void CallEmergency(){

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
/*
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
*/
        } else {
            //You already have permission
            try {
                context.startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:012345")));
                String[] smsBody =((MainActivity) MainActivity.context).mylocation;
                SmsManager smsManager = SmsManager.getDefault();
                String text = "#1 [긴급신고]\n"+smsBody[2]+"에서 사고가 발생하였습니다.";

                smsManager.sendTextMessage("012345",null,text,null,null);
                text = "#2 [긴급신고]\n"+smsBody[0]+",\n"+smsBody[1];
                smsManager.sendTextMessage("012345",null,text,null,null);

                AlertPush();

            } catch(Exception e) {
                Log.i("SMS","ERROR");
                e.printStackTrace();
            }
        }
    }

    public void AlertPush(){

        send_Data("alert_parent",student);
        send_Data("alert_teacher",null);

    }

    public void send_Data(String type, @Nullable Object obj){
        Log.i("실행순서","send_Data()");

        final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {


            }
        };

        Client client = new Client(type);
        client.setHandler(handler);
        client.setData(obj);
        client.start();

        try{
            client.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
