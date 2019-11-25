package com.example.youngeun.parentapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity{

    MaterialButton button;
    TextInputEditText id, pw;
    String loginID,loginPW;
    ImageView logo;
    CheckBox auto_login;
    public ArrayList<String> loginArrayList;

    public SharedPreferences AutoLogin;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (MaterialButton) findViewById(R.id.login_btn);
        id = (TextInputEditText) findViewById(R.id.id);
        pw = (TextInputEditText) findViewById(R.id.password);
        auto_login=(CheckBox)findViewById(R.id.auto_login);
        //logo = (ImageView) findViewById(R.id.logo);

        AutoLogin=getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
        editor=AutoLogin.edit();

        if(AutoLogin.getBoolean("auto_login",false)) {
            id.setText(AutoLogin.getString("ID", ""));
            pw.setText(AutoLogin.getString("PW", ""));
            auto_login.setChecked(true);

            loginID = AutoLogin.getString("ID", "");
            loginPW = AutoLogin.getString("PW", "");
            loginData newLogin = new loginData();
            newLogin.setLoginID(id.getText().toString());
            newLogin.setLoginPW(pw.getText().toString());

            try_login("login", newLogin);

            if (loginArrayList != null && loginArrayList.size() == 3) {
                String p_id = loginArrayList.get(0);
                String s_id = loginArrayList.get(2);

                Log.i("p_id", loginArrayList.get(0));
                Log.i("s_id", loginArrayList.get(2));

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("parentID", p_id);
                intent.putExtra("studentID", s_id);

                startActivity(intent);

                Toast.makeText(getApplicationContext(),p_id+"님 자동로그인",Toast.LENGTH_LONG).show();

            }
        }

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    String ID=id.getText().toString();
                    String PW=pw.getText().toString();

                    editor.putString("ID",ID);
                    editor.putString("PW",PW);
                    editor.putBoolean("auto_login",true);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }
            }


        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loginData newLogin = new loginData();
                newLogin.setLoginID(id.getText().toString());
                newLogin.setLoginPW(pw.getText().toString());

                try_login("login",newLogin);


                if(loginArrayList.size()==3){
                    String p_id=loginArrayList.get(0);
                    String s_id =loginArrayList.get(2);

                    Log.i("p_id",loginArrayList.get(0));
                    Log.i("s_id",loginArrayList.get(2));

                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("parentID",p_id);
                    intent.putExtra("studentID",s_id);

                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                }

                id.setText("");
                pw.setText("");



            }
        });


    }

    public byte[] getByteArrayFromDrawable(Drawable d) {
        //이미지 저장하기

        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        return data;
    }

    public Bitmap getImage(byte[] b) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }

    public void try_login(String type, @Nullable Object obj) {


        Client client = new Client(this, type);
        client.setData(obj);
        client.start();
        try {
            client.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

