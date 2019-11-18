package com.example.user.tidsnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.shape.RoundedCornerTreatment;

public class StudentDetail extends AppCompatActivity {

    RoundedBitmapDrawable rounded;
    ImageView profile;
    public static int sCorner = 15;
    public static String TAG = "TAG";
    public static int sMargin = 2;
    TextView name,age, gender, pnum,back,cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        profile=(ImageView)findViewById(R.id.profile);
        back=(TextView)findViewById(R.id.backbtn);
        name = (TextView)findViewById(R.id.detail_name);
        age = (TextView)findViewById(R.id.detail_age);

        gender = (TextView)findViewById(R.id.detail_gender);

        pnum = (TextView)findViewById(R.id.detial_pnum);

        cname = (TextView)findViewById(R.id.detail_classname);

        cname.setText(((MainActivity)MainActivity.context).class_info.getC_name()+"반");




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Student s = (Student)getIntent().getSerializableExtra("Student");
        Log.i(TAG,s.getPhone());
        Log.i(TAG,s.getStudent_name());
        Log.i(TAG,Integer.toString(s.getGender()));
        Log.i(TAG,Integer.toString(s.getAge()));

        name.setText("[ "+s.getStudent_name()+" ]");
        pnum.setText(s.getPhone());
        age.setText(Integer.toString(s.getAge())+"세");


        if (s.getGender()==1)
            gender.setText("여아");
        else
            gender.setText("남아");

        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("tidsnotedb", MODE_PRIVATE, null);
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM student_tbl where s_id = '"+s.getStudent_id()+"'", null);



        if (c!= null){


            c.moveToFirst();

            byte[] result =c.getBlob(c.getColumnIndex("s_portrait"));
            profile.setImageBitmap(getImage(result));




        }






    }

    public Bitmap getImage(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }
}
