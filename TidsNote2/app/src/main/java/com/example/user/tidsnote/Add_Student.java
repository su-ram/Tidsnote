package com.example.user.tidsnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.user.tidsnote.addActivity.REQUEST_IMAGE_CAPTURE;
import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;

public class Add_Student extends AppCompatActivity {

    AlertDialog alertDialog;
    Menu mymenu;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView name, addstudent;
    Intent intent,camera_intent;
    ImageView photo;
    String mCurrentPhotoPath;
    final int REQUEST_TAKE_PHOTO = 1;
    TextInputEditText sname,pname,pphone,sage;
    AutoCompleteTextView editTextFilledExposedDropdown, editTextFilledExposedDropdown2;
    Bitmap profile_bitmap;
    // Replace `<API endpoint>` with the Azure region associated with
// your subscription key. For example,
// apiEndpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0"
    private final String apiEndpoint = "https://koreacentral.api.cognitive.microsoft.com/face/v1.0/";
    //private final String apiEndpoint = "https://koreacentral.api.cognitive.microsoft.com/";

    // Replace `<Subscription Key>` with your subscription key.
// For example, subscriptionKey = "0123456789abcdef0123456789ABCDEF"
    //private final String subscriptionKey = "d291912aa2fe4da4a5076276050ecdbf";
    private final String subscriptionKey = "2b019826eb8f4953abecac5cfe2fa294";

    private final FaceServiceClient faceServiceClient =
            new FaceServiceRestClient(apiEndpoint, subscriptionKey);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__student);
        addstudent=(TextView)findViewById(R.id.addstudent);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.add_std_toolbar);

        sname=(TextInputEditText)findViewById(R.id.add_name);
        sage=(TextInputEditText)findViewById(R.id.add_age);
        pname=(TextInputEditText)findViewById(R.id.add_parent_name);
        pphone=(TextInputEditText)findViewById(R.id.add_parent_phone);

        photo=(ImageView)findViewById(R.id.add_photo);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("새로운 원아 등록");
        toolbar.setElevation(50f);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("얼굴을 찾을 수 없습니다. 다시 촬영해주세요.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                check_Permission();

            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog=builder.create();


        intent = getIntent();


        String[] GENDER = new String[] {"여아", "남아"};

        ArrayAdapter<String> adapter_gender =
                new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown,GENDER);

        editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter_gender);

        ArrayAdapter<String> adapter_class =
                new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown,new String[]{"4세반","5세반","6세반"});

        editTextFilledExposedDropdown2 = findViewById(R.id.filled_exposed_dropdown2);
        editTextFilledExposedDropdown2.setAdapter(adapter_class);

    }

    public boolean check_Data(){

        String name = sname.getText().toString();
        String age = sage.getText().toString();
        String parent = pname.getText().toString();
        String phone = pphone.getText().toString();
        String gender = editTextFilledExposedDropdown.getText().toString();
        String class_ = editTextFilledExposedDropdown2.getText().toString();
        String add_data[]={"","",""};
        add_data[0]=name;



        if (!name.equals("")&&!age.equals("")&&!parent.equals("")&&!phone.equals("")&&!gender.equals("")&&!class_.equals("")){


            //인텐트에 추가값 넣어줌.
            Student newStudent = (Student)intent.getSerializableExtra("student");
            newStudent.setStudent_name(name);
            newStudent.setParent_name(parent);
            newStudent.setPhone(phone);


            //성별 처리
            if (gender.equals("여아"))
                newStudent.setGender(1);
            else
                newStudent.setGender(2);

            //반 처리
            switch(class_){
                case "4세반":
                    newStudent.setClass_id("class01");
                    add_data[1]="class01";
                    break;
                case "5세반":
                    newStudent.setClass_id("class02");
                    add_data[1]="class02";
                    break;
                case "6세반":
                    newStudent.setClass_id("class03");
                    add_data[1]="class03";
                    break;


            }

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yymmdd");
                Date birth = simpleDateFormat.parse(age);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(birth);
                int birthyear = calendar.get(Calendar.YEAR);
                calendar.setTime(new Date());
                int curryear = calendar.get(Calendar.YEAR);
                newStudent.setAge(curryear-birthyear+1);

            }catch (Exception e){
                e.printStackTrace();
            }

            add_data[2]=((MainActivity)MainActivity.context).t_id;
            newStudent.setStudent_id(addPhoto(profile_bitmap,add_data));
            intent.putExtra("add",newStudent);

            return true;
        }

        return false;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add, menu);

        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_Data()) {
                    //모두 입력한 경우, 인텐트 설정하고 뒤로가기.

                    setResult(RESULT_OK,intent);
                    //((MainActivity)MainActivity.context).send_Data("add_student",null); //서버로 새로운 원아 정보 등록함.
                    onBackPressed();


                }else{


                    Snackbar.make(getWindow().getDecorView().getRootView(),"입력을 모두 완료하지 않았습니다.",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
/*
        SpannableString s = new SpannableString("추가");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        menu.getItem(1).setTitle(s);
        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //추가 버튼 눌렀을 경우,

                if(check_Data()) {
                    //모두 입력한 경우, 인텐트 설정하고 뒤로가기.

                setResult(RESULT_OK,intent);
                //((MainActivity)MainActivity.context).send_Data("add_student",null); //서버로 새로운 원아 정보 등록함.
                onBackPressed();


                }else{


                    Snackbar.make(getWindow().getDecorView().getRootView(),"입력을 모두 완료하지 않았습니다.",Snackbar.LENGTH_SHORT).show();
                }


                return false;
            }
        });
        menu.getItem(1).setVisible(false);
*/
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //카메라 실행

                camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                check_Permission();

                return false;
            }
        });


        mymenu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작

                onBackPressed();


                return true;
            }
            default:
                return super.onOptionsItemSelected(item) ;

        }
    }

    private Bitmap resize(Context context, Uri uri, int resize) {
        Bitmap resizeBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); // 1번

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); //3번
            resizeBitmap = bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resizeBitmap;
    }



    private void check_Permission(){

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(getApplicationContext(),"Granted",Toast.LENGTH_SHORT).show();
                //startActivityForResult(camera_intent,REQUEST_TAKE_PHOTO);

                dispatchTakePictureIntent();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                //Toast.makeText(getApplicationContext(),"Denied",Toast.LENGTH_SHORT).show();

            }
        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        //File file = new File(mCurrentPhotoPath);
                        //Bitmap bitmap = MediaStore.Images.Media
                          //      .getBitmap(getContentResolver(), Uri.fromFile(file));

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, options);



                        if (bitmap != null) {

                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            Bitmap rotatedBitmap = null;



                            //detectAndFrame();
                            //Bitmap resized = Bitmap.createScaledBitmap(src, src.getWidth(), src.getHeight(), true);

                            //rotatedBitmap=resize(getApplicationContext(),Uri.parse(mCurrentPhotoPath),4000);


                            switch(orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }



                            detectAndFrame(rotatedBitmap);
                            //photo.setImageBitmap(rotatedBitmap);




                        }
                    }
                    break;
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }




    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i("PhotoPath",mCurrentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.user.tidsnote.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    // Detect faces by uploading a face image.
// Frame faces after detection.
    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";

                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
                                    null          // returnFaceAttributes:
                                /* new FaceServiceClient.FaceAttributeType[] {
                                    FaceServiceClient.FaceAttributeType.Age,
                                    FaceServiceClient.FaceAttributeType.Gender }
                                */
                            );
                            if (result == null){
                                publishProgress(
                                        "Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(String.format(
                                    "Detection Finished. %d face(s) detected",
                                    result.length));
                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format(
                                    "Detection failed: %s", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                        //detectionProgressDialog.show();
                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        //detectionProgressDialog.setMessage(progress[0]);
                    }
                    @Override
                    protected void onPostExecute(Face[] result) {
                        //TODO: update face frames
                        //detectionProgressDialog.dismiss();

                        if(!exceptionMessage.equals("")){
                            showError(exceptionMessage);
                        }
                        if (result == null) {

                            Toast.makeText(getApplicationContext(),"Face detection failed",Toast.LENGTH_SHORT).show();
                        }




                        profile_bitmap = drawFaceRectanglesOnBitmap(imageBitmap, result);

                        if (profile_bitmap != null){
                        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
                        profile_bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
                        byte[] byteArray = stream.toByteArray() ;



                            photo.setImageBitmap(profile_bitmap);

                        }else{
                            alertDialog.show();
                        }



                        //imageBitmap.recycle();
                    }
                };

        detectTask.execute(inputStream);
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }})
                .create().show();
    }

    private static Bitmap drawFaceRectanglesOnBitmap(
            Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int[] point = new int[4];
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        if (faces != null) {
            for (Face face : faces) {

                //Log.i("Face",Integer.toString(faceRectangle.width)+", "+Integer.toString(faceRectangle.height)+", "+Integer.toString(faceRectangle.left)+", "+Integer.toString(faceRectangle.top));

                /*
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
*/

                Log.i("for",Integer.toString(0));




            }

            if (faces.length != 0 ) {
                FaceRectangle faceRectangle = faces[0].faceRectangle;
                point[0] = faceRectangle.left;
                point[1] = faceRectangle.top;
                point[2] = faceRectangle.width;
                point[3] = faceRectangle.height;
                bitmap=cropBitmap(bitmap,point);
            }else{

                bitmap=null;

            }


        }




        return bitmap;
    }

    static public Bitmap cropBitmap(Bitmap original,int[] point) {

        int x,y,w,h;


        w=point[2]+100;
        h=point[3]+200;

        if (point[0]<50){
            x=0;

        }
        else{
            x=point[0]-50;
        }
        if (point[1]<100) {
            y = 0;
            w=point[2]+50;
        }
        else {
            y = point[1] - 100;
        }

        if (x+w>original.getWidth())
            w=point[2];
        if (y+h>original.getHeight())
            h=point[3];

        Bitmap result = Bitmap.createBitmap(original
                , x//X 시작위치 (원본의 4/1지점)
                , y//Y 시작위치 (원본의 4/1지점)
                , w// 넓이 (원본의 절반 크기)
                , h); // 높이 (원본의 절반 크기)
        if (result != original) {
            original.recycle();
        }





        return result;
    }

    public byte[] getByteArrayFromBitmap(Bitmap bitmap){
        //이미지 저장하기

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();

        return data;
    }


    public String addPhoto(Bitmap bitmap,String[] str){
        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("tidsnotedb",MODE_PRIVATE,null);

        byte[] data = getByteArrayFromBitmap(bitmap);

        String id="", sql;

        //sql="delete from student_tbl where s_id > 'student21'";
        //sqLiteDatabase.execSQL(sql);

        Cursor c;
        c = sqLiteDatabase.rawQuery("select s_id from student_tbl order by s_id desc limit 1", null);
        while(c.moveToNext()){

             id = c.getString(c.getColumnIndex("s_id")).substring(7);

        }

        id="student"+Integer.toString(Integer.parseInt(id)+1);
        sql = "insert into student_tbl values(?,?,?,?,?)";
        //id,이름, class_id, t_id, photo

        SQLiteStatement p = sqLiteDatabase.compileStatement(sql);
        p.bindString(1,id);
        p.bindString(2,str[0]);
        p.bindString(3,str[1]);
        p.bindString(4,str[2]);
        p.bindBlob(5,data);
        p.execute();

        Log.i("Photo added","Added");






        return id;

    }


}