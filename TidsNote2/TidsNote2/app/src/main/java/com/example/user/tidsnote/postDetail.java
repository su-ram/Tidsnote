package com.example.user.tidsnote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class postDetail extends AppCompatActivity {


   TextView title,notice_detail,writer,time, back2list;
   ArrayList<byte[]> nphotos;
   int n_id=0;
   RecyclerView recyclerView;
   PhotoAdapter photoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetail);

        title=(TextView)findViewById(R.id.title_view);
        notice_detail=(TextView)findViewById(R.id.notice_datail);
        writer=(TextView)findViewById(R.id.notice_writer);
        time=(TextView)findViewById(R.id.notice_time);
        //recyclerView=(RecyclerView)findViewById(R.id.postdetail_recycler);

        Intent intent = getIntent();
        n_id = intent.getIntExtra("n_id",-1);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                Log.i("TAG","DONE");
                nphotos = (ArrayList<byte[]>)msg.obj;
            }
        };
        Client c = new Client("Notice_photo");

        c.setHandler(handler);
        c.setData(n_id);
        c.start();
        try{
            c.join();

        }catch (Exception e){
            e.printStackTrace();
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        //recyclerView.setLayoutManager(linearLayoutManager);
        //photoAdapter = new PhotoAdapter(getImage(nphotos));
        //recyclerView.setAdapter(photoAdapter);
        time.setText("|  "+intent.getStringExtra("date"));
        writer.setText(intent.getStringExtra("writer"));
        title.setText(intent.getStringExtra("title"));
        notice_detail.setText("\""+intent.getStringExtra("content")+"\"");
        back2list=findViewById(R.id.back2list);


        back2list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });
    }

    public ArrayList<Bitmap> getImage(ArrayList<byte[]> b){
        Bitmap bitmap;

        ArrayList<Bitmap> bitmaps= new ArrayList<>();

        if (b != null) {
            for (int i = 0; i < b.size(); i++) {
                bitmap = BitmapFactory.decodeByteArray(b.get(i), 0, b.get(i).length);
                bitmaps.add(bitmap);

            }
        }


        return bitmaps;
    }

}
