package com.example.user.gozip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.user.gozip.STT_NFC.setWindowFlag;


public class studentlist extends AppCompatActivity {



    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlist);
        fullScreen();
        Intent getintent = getIntent();
        final ArrayList<String> namelist = getintent.getStringArrayListExtra("names");
        String [] LIST_MENU=new String[namelist.size()/2];


        for(int i=0;i<namelist.size()/2;i++){
            LIST_MENU[i]=namelist.get(2*i+1);
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {


               final String [] data = new String[3];
                data[0]=namelist.get(position*2);
                data[1]=namelist.get(position*2+1);

                handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg){


                        Intent intent = new Intent(getApplicationContext(), Comment.class);

                        ArrayList<String> namelist = (ArrayList<String>) msg.obj;
                        if(namelist.get(0)!=null){
                            data[2]=namelist.get(0);
                        }
                        intent.putExtra("name", data);
                        startActivity(intent);

                        //Toast.makeText(getApplicationContext(), msg.toString(),Toast.LENGTH_LONG).show();


                    }
                };
                SendToServer client = new SendToServer(handler,"getComment");
                client.data=data;
                client.start();
/*
                Intent intent=new Intent(getApplicationContext(),Comment.class);
                intent.putExtra("name", data);
                startActivity(intent);*/

            }
        }) ;


    }

    public void fullScreen(){
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
