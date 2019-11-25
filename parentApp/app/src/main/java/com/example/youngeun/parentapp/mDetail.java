package com.example.youngeun.parentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class mDetail extends AppCompatActivity {

    TextView vmessage,vrequest,vdate;
    Button back_btn;
    String date,message,request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdetail);

        vdate=findViewById(R.id.vmdate);
        vmessage=findViewById(R.id.vmessage);
        vrequest=findViewById(R.id.vrequest);
        back_btn=findViewById(R.id.back_btn);
        Intent intent=getIntent();
        message=intent.getExtras().getString("comment");
        request=intent.getExtras().getString("request");
        date=intent.getExtras().getString("date");

        vdate.setText(date);
        vmessage.setText(message);
        vrequest.setText(request);

        back_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                finish();
            }
        });




    }

}
