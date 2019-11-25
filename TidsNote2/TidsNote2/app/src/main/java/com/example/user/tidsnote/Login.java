package com.example.user.tidsnote;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class Login extends AppCompatActivity {

    MaterialButton button;
    TextInputEditText id, pw;
    ImageView logo;
    CheckBox auto_login;
    String loginID,loginPW;
    public SharedPreferences AutoLogin;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (MaterialButton)findViewById(R.id.login_btn);
        id = (TextInputEditText)findViewById(R.id.id);
        pw=(TextInputEditText)findViewById(R.id.password);
        auto_login=(CheckBox)findViewById(R.id.auto_login);
        //logo=(ImageView)findViewById(R.id.logo);

        Intent intent = getIntent();
        final String student_id = intent.getStringExtra("student_id");
        //Toast.makeText(getApplicationContext(),student_id,Toast.LENGTH_SHORT).show();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String deviceToken = instanceIdResult.getToken();
                // Do whatever you want with your token now
                // i.e. store it on SharedPreferences or DB
                // or directly send it to server

                Log.d("IDService", "device token : " + deviceToken);
            }});
        //sendNotification("Title","Body");

        /*auto login*/
        AutoLogin=getSharedPreferences("AutoLogin",Activity.MODE_PRIVATE);
        editor=AutoLogin.edit();

        if(AutoLogin.getBoolean("auto_login",false)){
            id.setText(AutoLogin.getString("ID",""));
            pw.setText(AutoLogin.getString("PW",""));
            auto_login.setChecked(true);

            loginID=AutoLogin.getString("ID","");
            loginPW=AutoLogin.getString("PW","");

            final String login[] = {"",""};

            login[0] = id.getText().toString();
            login[1] = pw.getText().toString();






            Handler handler = new Handler(){

                @Override
                public void handleMessage(Message msg) {




                    if (msg.obj != null){
                        ArrayList<String> result = (ArrayList<String>)msg.obj;
                        //initMember(Integer.parseInt(result.get(3)));
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("student_id",student_id);
                        intent.putExtra("login_id",result);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),id.getText()+"님 자동로그인",Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();

                        id.requestFocus();
                        id.setText("");
                        pw.setText("");

                    }



                }
            };

            Client client = new Client("login");
            client.setHandler(handler);
            client.setData(login);
            client.start();
            try {
                client.join();

            }catch (Exception e){
                e.printStackTrace();
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

                final String login[] = {"",""};

                login[0] = id.getText().toString();
                login[1] = pw.getText().toString();






                Handler handler = new Handler(){

                    @Override
                    public void handleMessage(Message msg) {




                        if (msg.obj != null){
                            ArrayList<String> result = (ArrayList<String>)msg.obj;
                            //initMember(Integer.parseInt(result.get(3)));
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("student_id",student_id);
                            intent.putExtra("login_id",result);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();

                            id.requestFocus();
                            id.setText("");
                            pw.setText("");

                        }



                    }
                };

                Client client = new Client("login");
                client.setHandler(handler);
                client.setData(login);
                client.start();
                try {
                    client.join();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }

    public void initMember(int i){


        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("tidsnotedb", MODE_PRIVATE, null);

        int sum=0;
        String sql = " select count(*) as sum from student_tbl";
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        if (c!= null){


            c.moveToFirst();

            sum=c.getInt(c.getColumnIndex("sum"));
        }

        if (sum > i){
            sql = "delete from student_tbl where s_id > '"+"student"+Integer.toString(i)+"'";
            sqLiteDatabase.execSQL(sql);
        }



    }
    private void sendNotification(String messageTitle, String messageBody) {


        Intent intent = new Intent(this, Login.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            try {
                String channelId = getString(R.string.default_notification_channel_id);
                CharSequence name = getString(R.string.default_notification_channel_name);
                String description = getString(R.string.default_notification_channel_description);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(channelId, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                                .setSmallIcon(R.drawable.send)
                                .setContentTitle(messageTitle)
                                .setContentText(messageBody)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                        ;
                notificationManager.notify(0, notificationBuilder.build());
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

    }



}
