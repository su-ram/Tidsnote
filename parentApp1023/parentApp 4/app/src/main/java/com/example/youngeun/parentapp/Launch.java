package com.example.youngeun.parentapp;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Launch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);

        try{
            Thread.sleep(2000);

        }catch (Exception e){
            e.printStackTrace();

        }

        startActivity(new Intent(this, Login.class));
        finish();

    }
}


