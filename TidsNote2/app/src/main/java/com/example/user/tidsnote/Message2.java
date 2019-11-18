package com.example.user.tidsnote;

import android.content.Context;
import android.widget.Toast;

public class Message2 {
    public static void message(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
