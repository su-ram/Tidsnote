package com.example.user.tidsnote;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import static com.example.user.tidsnote.Message2.message;

public class addActivity extends AppCompatActivity {

    static  final int REQUEST_IMAGE_CAPTURE=1;
    private String imageFilePath;
    private Uri photoUri;
    Button camera_btn,sign_btn,cancel_btn;
    EditText name_input,class_input,age_input,pnum_input,memo_input;
    myDBAdapter helper;
    LinearLayout set_fragment;
    ImageView imageview;
    studentData studentdata;
    final static String dbName="student.db";
    final static int dbVersion=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        camera_btn=findViewById(R.id.camera_btn);
        sign_btn=findViewById(R.id.sign_btn);
        cancel_btn=findViewById(R.id.cancel_btn);
        imageview=findViewById(R.id.imageview);

        name_input = (EditText) findViewById(R.id.name_input);
        age_input = (EditText) findViewById(R.id.age_input);
        class_input = (EditText) findViewById(R.id.class_input);
        pnum_input=(EditText)findViewById(R.id.pnum_input);
        memo_input=(EditText)findViewById(R.id.memo_input);
        helper = new myDBAdapter(this);

        camera_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });


        cancel_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(addActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    public void signStudent(View v) {
        String name_in = name_input.getText().toString();
        String age_in=age_input.getText().toString();
        String class_in=class_input.getText().toString();
        String pnum_in=pnum_input.getText().toString();
        String memo_in=memo_input.getText().toString();

        if(name_in.isEmpty()||age_in.isEmpty()||class_in.isEmpty()||pnum_in.isEmpty()){
            message(getApplicationContext(),"정보를 입력해주세요.");

        }else{
            long id=helper.insertData(name_in,age_in,class_in,pnum_in,memo_in);
            studentdata.setStdName(name_in);
            studentdata.setStdAge(age_in);
            studentdata.setStdClass(class_in);
            studentdata.setStdPnum(pnum_in);
            studentdata.setStdMemo(memo_in);

            if(id<=0){
                message(getApplicationContext(),"에러발생");
                name_input.setText("");
                age_input.setText("");
                class_input.setText("");
                pnum_input.setText("");
                memo_input.setText("");

            }else{
                message(getApplicationContext(),name_input.getText()+"등록완료");
                name_input.setText("");
                age_input.setText("");
                class_input.setText("");
                pnum_input.setText("");
                memo_input.setText("");
            }
        }

    }

    public void viewdata(View view){
        String data=helper.getData();
        message(this,data);

    }
    static class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
            super(context,name,factory,version);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE student (name TEXT,class TEXT,age TEXT,pnum phone,memo TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP TABLE IF EXISTS student");
            onCreate(db);
        }
    }
    private void sendTakePhotoIntent(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile=null;
            try{
                photoFile=createImageFile();

            }catch (IOException e){

            }
            if(photoFile!=null){
                photoUri=FileProvider.getUriForFile(this,getPackageName(),photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        imageview.setImageBitmap(bitmap);
        if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK){
            bitmap=BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif=null;

            try{
                exif=new ExifInterface(imageFilePath);
            }catch(IOException e){
                e.printStackTrace();
            }
            int exifOrientation;
            int exifDegree;

            if(exif!=null){
                exifOrientation=exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
                exifDegree=exifOrientationToDegrees(exifOrientation);
            }else{
                exifDegree=0;
            }
            ((ImageView)findViewById(R.id.photo)).setImageBitmap(rotate(bitmap,exifDegree));
        }

    }

    private File createImageFile() throws IOException{
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="TEST_"+timeStamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image =File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath=image.getAbsolutePath();
        return image;
    }
    private int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return  0;
    }

    private Bitmap rotate(Bitmap bitmap,float degree){
        Matrix matrix=new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }


}



