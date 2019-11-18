package com.example.youngeun.parentapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MessageActivity extends AppCompatActivity {


    private TextView mdate;
    private Button send_btn;
    public EditText message,request;
    RecyclerViewAdapter mAdapter;
    ArrayList<messageData> messageDataset;
    ArrayList<messageData> tmpMsgList=new ArrayList<>();
    ArrayList<String> lately_mid=new ArrayList<>();
    ArrayList<String> temp_count=new ArrayList<>();
    ArrayList<String> teacher_active=new ArrayList<>();
    private MainActivity mainActivity;
    private MessageTab messageTab;
    String cur_mid;
    int tmp_mid=0;
    String count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        mAdapter=new RecyclerViewAdapter(messageDataset);
        mdate = findViewById(R.id.mdate);
        send_btn = findViewById(R.id.send_btn);
        message = findViewById(R.id.message);
        request=findViewById(R.id.request);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat t = new SimpleDateFormat("HHmm");
        final String curDate = s.format(new Date());
        final String m_date = curDate;
        final String curTime=t.format(new Date());
        final int cur_time=Integer.parseInt(curTime);
        mdate.setText(curDate);

        send_id("teacher_active",myIDs.s_id);
        send_id("temp_count",myIDs.p_id);

        if (!temp_count.get(0).equals("0")){

            send_id("get_temp_message",myIDs.p_id);
            message.setText(tmpMsgList.get(0).message);
            request.setText(tmpMsgList.get(0).request);
            tmp_mid=Integer.parseInt(tmpMsgList.get(0).m_id);


        }

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!teacher_active.get(0).equals("1")) {

                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                    alertDlg.setTitle("작성시간제한");

                    alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String temp_content1 = message.getText().toString();
                            String temp_content2 = request.getText().toString();


                            messageData newMessage = (messageData) getIntent().getSerializableExtra("message");
                            if(temp_count.get(0).equals("0")) {
                                send_id("lately_mid", "");
                                Log.i("lately m_id:", lately_mid.get(0));

                                int cur_mid_int = Integer.parseInt(lately_mid.get(0));
                                cur_mid_int = cur_mid_int + 1;

                                cur_mid = String.valueOf(cur_mid_int);
                            }else {
                                cur_mid=String.valueOf(tmp_mid);
                            }
                            newMessage.setP_id(myIDs.p_id);
                            newMessage.setMessage(temp_content1);
                            newMessage.setRequest(temp_content2);
                            newMessage.setMdate(m_date);
                            newMessage.setM_id(cur_mid);
                            newMessage.setState("temp");

                            Intent intent = new Intent();
                            intent.putExtra("message", newMessage);
                            setResult(RESULT_OK, intent);

                            dialog.dismiss();  // AlertDialog를 닫는다.
                            finish();
                        }
                    });

                    // '아니오' 버튼이 클릭되면
                    alertDlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();  // AlertDialog를 닫는다.
                        }
                    });


                    alertDlg.setMessage("임시저장 하시겠습니까?");
                    alertDlg.show();


                } else {
                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                    alertDlg.setTitle("코멘트 전달");

                    alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String date = mdate.getText().toString();
                            String content1 = message.getText().toString();
                            String content2 = request.getText().toString();

                            if (content1.isEmpty() && content2.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "insert comment", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();  // AlertDialog를 닫는다.
                                finish();
                            } else {


                                messageData newMessage = (messageData) getIntent().getSerializableExtra("message");
                                if(temp_count.get(0).equals("0")) {
                                    send_id("lately_mid", "");//m_id
                                    Log.i("lately m_id:", lately_mid.get(0));

                                    int cur_mid_int = Integer.parseInt(lately_mid.get(0));
                                    cur_mid_int = cur_mid_int + 1;

                                    cur_mid = String.valueOf(cur_mid_int);
                                }else{
                                    cur_mid=String.valueOf(tmp_mid);
                                }
                                newMessage.setP_id(myIDs.p_id);
                                newMessage.setMessage(content1);
                                newMessage.setRequest(content2);
                                newMessage.setMdate(date);
                                newMessage.setM_id(cur_mid);
                                newMessage.setState("final");

                                Intent intent = new Intent();
                                intent.putExtra("message", newMessage);
                                setResult(RESULT_OK, intent);

                                message.setText("");
                                Toast.makeText(getApplicationContext(), "adapter ok", Toast.LENGTH_LONG).show();


                                dialog.dismiss();  // AlertDialog를 닫는다.
                                finish();
                            }
                            // messageDataset = new ArrayList<>();
                            // messageDataset.add(new messageData("student01", date, content));

                            // send_Data("send_message", messageDataset);
                            Toast.makeText(getApplicationContext(), message.getText() + "ok", Toast.LENGTH_LONG).show();


                        }
                    });

                    // '아니오' 버튼이 클릭되면
                    alertDlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();  // AlertDialog를 닫는다.
                        }
                    });


                    alertDlg.setMessage("보내시겠습니까?");
                    alertDlg.show();


                }

            }
        });

    }

    public void send_id(String type, @Nullable String str) {

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };

        Client client = new Client(this, type);
        client.setData(str);
        client.start();
        try {
            client.join();
            Log.i("Init", "Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void send_temp(String type, @Nullable Object object) {

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };

        Client client = new Client(this, type);
        client.setData(object);
        client.start();
        try {
            client.join();
            Log.i("Init", "Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}