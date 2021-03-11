package com.example.user.tidsnote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.audiofx.NoiseSuppressor;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.ByteArrayOutputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.user.tidsnote.menu2fragment.getOrientation;
import static com.example.user.tidsnote.menu2fragment.rotateImage;

public class MainActivity extends AppCompatActivity implements FragmentListener{

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private menu1fragment menu1fragment = new menu1fragment();
    private menu2fragment menu2fragment = new menu2fragment();
    private menu3fragment menu3fragment = new menu3fragment();
    private menu4fragment menu4fragment = new menu4fragment();
    androidx.appcompat.widget.Toolbar toolbar;
    CardView cardView;
    TextView button;
    MenuInflater menuInflater;
    Menu mymenu;
    Bundle bundle;
    ArrayList<Student> arrayList;
    int pos;
    static public Context context;
    IntentFilter[] readTag;
    String nfc_result = "nfc message";
    ArrayList<postData> postDataArrayList;
    ArrayList<Notice> noticeList,mylist;
    ArrayList<Request> requestList;
    PostListAdapter postListAdapter, newsfeedAdapter;
    StudentAdapter addStudentAdapter;
    BottomNavigationView bottomNavigationView;
    private final int PERMISSION = 1;
    final int PICTURE_REQUEST_CODE = 200;
    RecognitionProgressView recognitionProgressView;
    SpeechRecognizer mRecognizer;
    String stt, t_id, t_name, t_pnum;
    TextView textView, alert_new;
    ArrayList<String> menulist;
    Class_ class_info;
    SwitchMaterial switchMaterial;
    SpeechRecognizer speechRecognizer;
    private GpsTracker gpsTracker;
    String[] mylocation;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initDB();

        Log.i("실행순서","onCreate()");



        setContentView(R.layout.activity_main);
        context = this;

        mylocation=myLocation();



        bottomNavigationView = findViewById(R.id.bottomNavigation);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        cardView = (CardView) findViewById(R.id.card_banner);
        button = (TextView) findViewById(R.id.btn_ok);
        alert_new=(TextView)findViewById(R.id.alert_new);
        switchMaterial = (SwitchMaterial)findViewById(R.id.switch_);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menu1fragment).commitAllowingStateLoss();


        Intent intent = getIntent();
        ArrayList<String> login_result = intent.getStringArrayListExtra("login_id");
        t_id=login_result.get(0);
        t_name=login_result.get(1);
        t_pnum=login_result.get(2);


        try {

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String deviceToken = instanceIdResult.getToken();

                    Log.d("IDService","device token : "+deviceToken);

                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        send_Data("init",login_result.get(0));//초기화를 위해 선생님 id 서버로 보냄

        initArrayList();

        postListAdapter = new PostListAdapter(noticeList);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.menu1: {
                        transaction.replace(R.id.frame_layout, menu1fragment).commitAllowingStateLoss();
                        toolbar.setTitle("TidsNote");
                        mymenu.getItem(0).setVisible(false);
                        mymenu.getItem(1).setVisible(false);
                        mymenu.getItem(2).setVisible(false);


                        break;
                    }

                    case R.id.menu2: {
                        transaction.replace(R.id.frame_layout, menu2fragment).commitAllowingStateLoss();

                        mymenu.getItem(0).setVisible(false);
                        mymenu.getItem(1).setVisible(true);
                        mymenu.getItem(2).setVisible(true);

                        toolbar.setTitle("원아목록");


                        break;
                    }
                    case R.id.menu3: {
                        transaction.replace(R.id.frame_layout, menu3fragment).commitAllowingStateLoss();
                        //menu3fragment.changeTab();
                        toolbar.setTitle("공지사항");
                        mymenu.getItem(0).setVisible(false);
                        mymenu.getItem(1).setVisible(false);
                        mymenu.getItem(2).setVisible(false);


                        break;
                    }
                    case R.id.menu4: {
                        transaction.replace(R.id.frame_layout, menu4fragment).commitAllowingStateLoss();
                        toolbar.setTitle("설정");
                        mymenu.getItem(0).setVisible(false);
                        mymenu.getItem(1).setVisible(false);
                        mymenu.getItem(2).setVisible(false);


                        break;
                    }
                }


                return true;
            }
        });





        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //Snackbar.make(getWindow().getDecorView().getRootView(), "On", Snackbar.LENGTH_SHORT).show();
                    send_Data("on",t_id);
                }else {
                    //Snackbar.make(getWindow().getDecorView().getRootView(), "Off", Snackbar.LENGTH_SHORT).show();
                    send_Data("off",t_id);

                }
            }
        });




    }


    @Override
    public void onResume() {
        super.onResume();

        Log.i("실행순서","onResume()");


        if (menu2fragment.StudentAdapter != null)
            menu2fragment.StudentAdapter.notifyItemChanged(this.pos);

        if (postListAdapter != null && newsfeedAdapter != null) {
            //postListAdapter.notifyDataSetChanged();
            postListAdapter.notifyDataSetChanged();
            newsfeedAdapter.notifyDataSetChanged();
        }

        if (addStudentAdapter != null) {
            addStudentAdapter.notifyDataSetChanged();
            //Toast.makeText(getApplicationContext(),"add student",Toast.LENGTH_SHORT).show();
            //menu2fragment.addPhoto(bitmap);

        }

        if (menu2fragment.photoAdapter != null){
            menu2fragment.photoAdapter.notifyDataSetChanged();
        }


    }

    public void viewMoreNotice(){
        changeBottomMenu(2);
        Bundle b = new Bundle();
        b.putBoolean("more",true);
        menu3fragment.setArguments(b);
    }


    public String TagedNfc() {

        Log.i("실행순서","TagedNfc()");

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            ndef.addDataType("application/com.tidsnote2.example.android.nfc");
        } catch (IntentFilter.MalformedMimeTypeException e) {

        }

        IntentFilter[] nfcIntentFilter = new IntentFilter[]{ndef};
        readTag = nfcIntentFilter;

        if (getIntent().getAction() != null &&getIntent().getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {


            Parcelable[] rawMessages =
                    getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (rawMessages != null) {

                NdefMessage[] msg = new NdefMessage[rawMessages.length];

                for (int i = 0; i < rawMessages.length; i++) {
                    msg[i] = (NdefMessage) rawMessages[i];
                }

                nfc_result = new String(msg[0].getRecords()[0].getPayload());

                //STT();
                if (nfc_result.charAt(0) == 's') {
                    changeBottomMenu(1);

                    String stu_id = nfc_result.substring(1);


                    if (stu_id.length()==1){
                        stu_id= "0"+stu_id;
                    }


/*
                    Intent intent = new Intent(getApplicationContext(), InputForm.class);
                    intent.putExtra("Student", arrayList.get(findStudent("student"+stu_id)));
                    intent.putExtra("STT","STT");
                    startActivityForResult(intent, 000);
*/


                    if (findStudent(stu_id) == -1){
                        //다른반 원아인 경우
                        showSTTDialog(StudentFromDB("student"+stu_id));

                    }else {
                        showSTTDialog(arrayList.get(findStudent("student" + stu_id)));
                    }

                }


            }

        }


        return nfc_result;
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//onResume()보다 먼저 호출됨.
        Log.i("실행순서","onActivityResult()"+String.valueOf(resultCode));


        ArrayList<String> result;

        if (requestCode == GPS_ENABLE_REQUEST_CODE){



            if (checkLocationServicesStatus()) {
                if (checkLocationServicesStatus()) {

                    Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                    checkRunTimePermission();
                    return;
                }
            }

        }


        if (requestCode == 000) {


            result = data.getStringArrayListExtra("result");
            pos = Integer.parseInt(result.get(4));


            if (resultCode == 1234) {//모두 입력한 경우


                Student student = arrayList.get(pos);

                student.setComment(result.get(0));
                student.setStatus(Integer.parseInt(result.get(1)), Integer.parseInt(result.get(2)), Integer.parseInt(result.get(3)));
                student.setDate(new Date());
                student.setDone(true);

                Log.i("Save Status", student.getComment() + ", " + Boolean.toString(student.isDone()));

                //send_Data("writing",student);
                //send_Data("student_photos",UriToByte(data.getStringArrayListExtra("uris")));


                try{

                    ArrayList<String> uris = data.getStringArrayListExtra("uris");
                    Client c = new Client(this, "writing");
                    c.setData(student);
                    c.setUrilist(UriToByte(uris));
                    Log.i("URIS", String.valueOf(uris.size()));
                    c.start();
                    c.join();





                }catch (Exception e){
                    e.printStackTrace();
                }








                Snackbar.make(getWindow().getDecorView().getRootView(), "저장 : " + student.getStudent_name(), Snackbar.LENGTH_SHORT).show();


            } else if (resultCode==5678){//일부분만 입력한 경우, 임시 저장 필요

                Student student = arrayList.get(pos);
                student.setComment(result.get(0));
                student.setStatus(Integer.parseInt(result.get(1)), Integer.parseInt(result.get(2)), Integer.parseInt(result.get(3)));
                student.setDone(false);
                student.setDate(new Date());

                send_Data("writing",student);
                Log.i("STATUS", student.getComment()+String.valueOf(student.getStatus()[0]));

                Snackbar.make(getWindow().getDecorView().getRootView(), "임시 저장 : " + student.getStudent_name(), Snackbar.LENGTH_SHORT).show();

            }else if (requestCode==910){
                //아예 입력 안 한 경우

                Student student = arrayList.get(pos);
                Snackbar.make(getWindow().getDecorView().getRootView(), "입력 안함 : " + student.getStudent_name(), Snackbar.LENGTH_SHORT).show();


            }else if (requestCode == 4444){
                Log.i("4444","Nothing changed");

            }
        } else if (requestCode == 111) {
            //새로운 공지사항 등록 완료한 경우

            if (data!=null) {
                Notice newNotice = (Notice) data.getSerializableExtra("notice");
                if (newNotice != null) {
                    noticeList.add(0, newNotice);
                    mylist.add(0,newNotice);
                    //menu3fragment.chattingTab.mylist.add(newNotice);

                    ArrayList<String> urilist = data.getStringArrayListExtra("uri");
                    Bitmap bitmap;
                    Uri uri;
                    ByteArrayOutputStream stream;
                    ArrayList<byte[]> bytelist = new ArrayList<>();

                    for (int i=0;i<urilist.size();i++){
                        Log.i("URI",urilist.get(i).toString());
                        uri = Uri.parse(urilist.get(i));
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] bytes = stream.toByteArray();
                            bytelist.add(bytes);

                        }catch (Exception e){

                        }
                    }

                    //newNotice.setPhotos(bytelist);
                    newNotice.setPhotos(UriToByte(data.getStringArrayListExtra("uri")));
                    send_Data("add_notice",newNotice);

                }
            }
        } else if (requestCode == 2222) {
            //새로운 원아 등록 완료한 경우

            if (data != null) {
                Student newStudent = (Student) data.getSerializableExtra("add");
                if (newStudent != null && (newStudent.getClass_id()).equals(class_info.getC_id())) {
                    newStudent.setPos(arrayList.size());
                    arrayList.add(newStudent);
                }

                send_Data("add_student",newStudent);
            }
        }
    }

    public ArrayList<Notice> makeNewList(ArrayList<Notice> noticeArrayList, String id){

        ArrayList<Notice> mylist = new ArrayList<>();

        for (int i=0;i<noticeArrayList.size();i++){

            if ((noticeArrayList.get(i).getT_id()).equals(id)){
                mylist.add(noticeArrayList.get(i));
            }
        }


        return mylist;

    }

    public ArrayList<byte[]> UriToByte(ArrayList<String> urilist){

        Bitmap bitmap;
        Uri uri;
        ByteArrayOutputStream stream;
        ArrayList<byte[]> bytelist = new ArrayList<>();

        for (int i=0;i<urilist.size();i++){
            Log.i("URI",urilist.get(i).toString());
            uri = Uri.parse(urilist.get(i));
            try {
                int orientation = getOrientation(this,uri);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap rotate;
                switch(orientation) {

                    case 90:
                        rotate = rotateImage(bitmap, 90);
                        break;

                    case 180:
                        rotate = rotateImage(bitmap, 180);
                        break;

                    case 270:
                        rotate = rotateImage(bitmap, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotate = bitmap;
                }
                stream = new ByteArrayOutputStream();
                rotate.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                bytelist.add(bytes);

            }catch (Exception e){

            }
        }

        return bytelist;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("실행순서","onCreateOptionsMenu()");

        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        this.mymenu = menu;

        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
        SpannableString s = new SpannableString("전송");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        menu.getItem(2).setTitle(s);


        String stt_id = getIntent().getStringExtra("student_id");
        if (stt_id!=null){
            if (findStudent(stt_id)!=-1){

                changeBottomMenu(1);

                Intent intent = new Intent(this, InputForm.class);
                intent.putExtra("Student", arrayList.get(findStudent(stt_id)));
                intent.putExtra("position",arrayList.get(findStudent(stt_id)).getPos());
                intent.putExtra("stt",true);

                startActivityForResult(intent, 000);

            }else{
                Student student = StudentFromDB(stt_id);
                STT start_STT = new STT((MainActivity) this, this, student);

                speechRecognizer = start_STT.showSTTDialog(student);            }
        }



        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer!=null){
            speechRecognizer.destroy();
            speechRecognizer=null;

        }
    }

    public void changeBottomMenu(int pos) {
        Log.i("실행순서","changeBottomMenu()");



        switch (pos) {
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.menu1);
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.menu2);
                break;
            case 2:
                bottomNavigationView.setSelectedItemId(R.id.menu3);
                //menu3fragment.changeTab();

                break;
            case 3:
                bottomNavigationView.setSelectedItemId(R.id.menu4);
                break;
            default:
                break;
        }

    }

    public int findStudent(String id) {
        Log.i("실행순서","findStudent()");


        Log.i("Test",Integer.toString(arrayList.size()));
        for (int i = 0; i < arrayList.size(); i++) {

            if ((arrayList.get(i).getStudent_id()).equals(id))
                return i;
        }

        return -1;

    }

    public boolean check_Done() {
        Log.i("실행순서","check_Done()");


        for (int i = 0; i < arrayList.size(); i++) {

            if (!arrayList.get(i).isDone())
                return false;
        }

        return true;
    }

    public void send_All(String str, ArrayList<String> urilist) {

        if (check_Done()) {

            //모두 작성한 경우
            if (!str.equals("")){
                //공통사항이 존재하는 경우

                for (int i=0;i<arrayList.size();i++){
                    arrayList.get(i).setCommon(str);
                    Log.i("Add_ALL",arrayList.get(i).getComment()+arrayList.get(i).getCommon());

                }
            }

            Snackbar.make(getWindow().getDecorView().getRootView(),"일괄 전송합니다.",1000).show();
            String done[] = {"",""};
            done[0]=class_info.getC_id();
            done[1]=str;
            send_Data("send_all",done);//서버에 모든 원아수첩 데이터 전송함.
            //send_Data("send_all_photo",urilist);
            try{

                Client c = new Client(this, "send_all_photo");
                c.setData(done[0]);
                c.setUrilist(UriToByte(urilist));
                Log.i("URIS", String.valueOf(urilist.size()));
                c.start();
                c.join();





            }catch (Exception e){
                e.printStackTrace();
            }
            Log.i("공통사항 사진들",String.valueOf(urilist.size()));






        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(),"모두 작성하지 않았습니다.",1000).show();
        }

    }

    public void send_Data(String type, @Nullable Object obj){
        Log.i("실행순서","send_Data()"+type);

         final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {

                menu4fragment.setClass_((Class_) msg.obj);
                class_info=(Class_)msg.obj;
                menu2fragment.menulist=menulist;
            }
        };



        Client client = new Client(this,type);
        client.setHandler(handler);
        client.setData(obj);
        client.start();
        try{
            client.join();

        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public void initArrayList(){
        Log.i("실행순서","initArrayList()");

        postDataArrayList = new ArrayList<>();

        getPhoto(this.requestList);
        menu1fragment.requestArrayList=this.requestList;
        menu2fragment.list = arrayList;
        this.mylist = makeNewList(this.noticeList,this.t_id);
        //menu3fragment.postDataArrayList = this.postDataArrayList;
        menu4fragment.students = arrayList;

        alert_new.setText(Integer.toString(requestList.size())+"개의 학부모 요청사항과\n"+Integer.toString(noticeList.size())+"개의 공지사항이 있습니다.");

    }

    public void showSTTDialog(final Student student){


        final View view = (View) getLayoutInflater().
                inflate(R.layout.stt_dialog, null);
        recognitionProgressView = (RecognitionProgressView)view.findViewById(R.id.stt_progress);
         textView= (TextView)view.findViewById(R.id.stt_txt);

        MaterialButton micbutton = (MaterialButton)view.findViewById(R.id.mic_button);
        MaterialButton sendbutton = (MaterialButton)view.findViewById(R.id.send_stt);




        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(student.getStudent_name());

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        STT(view);


        micbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognitionProgressView.stop();
                recognitionProgressView.play();
                startRecognition();
            }
        });

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stt != null && !stt.equals("")){
                    student.addStt_message(stt);
                    student.setDate(new Date());
                    send_Data("add_stt",student);

                    }
                dialog.dismiss();

            }
        });

        dialog.show();








    }

    private void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko");
        mRecognizer.startListening(intent);
    }

    public void setSTT(String str){

        textView.setText("\" "+ str+" \"");


    }

    public void STT(View view){



        if(Build.VERSION.SDK_INT >= 23){


            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO
            }, PERMISSION);
        }



        int[] colors = {
                ContextCompat.getColor(this, R.color.color1),
                ContextCompat.getColor(this, R.color.color2),
                ContextCompat.getColor(this, R.color.color3),
                ContextCompat.getColor(this, R.color.color4),
                ContextCompat.getColor(this, R.color.color5)
        };

        int[] heights = { 20, 54, 66, 43, 36 };


        mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);

        recognitionProgressView.setSpeechRecognizer(mRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {

                ArrayList<String> matches =
                        results.getStringArrayList(
                                SpeechRecognizer.RESULTS_RECOGNITION);

                stt = matches.get(0);
                setSTT(stt);

                //Toast.makeText(getApplicationContext(),matches.get(0),Toast.LENGTH_SHORT).show();


            }
        });

        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.setCircleRadiusInDp(10);
        recognitionProgressView.setSpacingInDp(10);
        recognitionProgressView.setIdleStateAmplitudeInDp(10);
        recognitionProgressView.setRotationRadiusInDp(20);
        recognitionProgressView.play();

        startRecognition();


        //mRecognizer.setRecognitionListener(listener);
        //mRecognizer.startListening(sttIntent);



    }
    public Student StudentFromDB(String id){

        Student tagedStudent = new Student();


        try {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("tidsnotedb", MODE_PRIVATE, null);
            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM student_tbl where s_id = '"+id+"'", null);



            if (c!= null){


                c.moveToFirst();



                String name = c.getString(c.getColumnIndex("s_name"));
                String c_id = c.getString(c.getColumnIndex("c_id"));
                String t_id = c.getString(c.getColumnIndex("t_id"));

                tagedStudent.setStudent_name(name);
                tagedStudent.setStudent_id(id);
                tagedStudent.setClass_id(c_id);




            }



        }catch (Exception e){
            e.printStackTrace();
        }


        return tagedStudent;

    }

    /*
    public void initDB(){


        try{

            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("tidsnotedb",MODE_PRIVATE,null);
            byte[] bytes;
            SQLiteStatement p;
            String sql = "create table if not exists student_tbl (s_id varchar(10), s_name varchar(10), c_id varchar(10), t_id varchar(10))";
            sqLiteDatabase.execSQL(sql);

            sql = "delete from student_tbl where s_id > 'student06'";
            sqLiteDatabase.execSQL(sql);

            sql = "insert into student_tbl values('student01','김태형','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student07));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student02','김태형','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student07));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student03','김태형','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student07));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student04','김태형','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student07));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student05','김태형','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student07));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student06','김태형','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student07));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();


            sql = "insert into student_tbl values('student07','김태형','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student07));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student08','전정국','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student08));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student09','박지민','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student09));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student10','정호석','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student10));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student11','민윤기','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student11));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student12','김석진','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student12));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student13','김남준','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student13));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student14','황미영','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student14));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student15','이순규','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student15));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student16','김태연','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student16));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student17','임윤아','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student17));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student18','김효연','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student18));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student19','최수영','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student19));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student20','권유리','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student20));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student21','서주현','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student21));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();


        }catch (Exception e){

        }


    }
*/
    public void initDB(){


        try{


            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("tidsnotedb",MODE_PRIVATE,null);
            byte[] bytes;
            SQLiteStatement p;
            String sql="drop table student_tbl";
            //sqLiteDatabase.execSQL(sql);

            sql = "create table student_tbl (s_id varchar(10), s_name varchar(10), c_id varchar(10), t_id varchar(10),s_portrait blob)";
            sqLiteDatabase.execSQL(sql);

            sql = "insert into student_tbl values('student01','손나은','class01','teacher01',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student01));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student02','윤보미','class01','teacher01',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student02));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student03','오하영','class01','teacher01',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student03));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student04','김남주','class01','teacher01',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student04));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student05','정은지','class01','teacher01',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student05));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student06','박초롱','class01','teacher01',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student06));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student07','김태형','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student07));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student08','전정국','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student08));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student09','박지민','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student09));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student10','정호석','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student10));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student11','민윤기','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student11));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student12','김석진','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student12));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student13','김남준','class02','teacher02',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student13));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student14','황미영','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student14));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student15','이순규','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student15));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student16','김태연','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student16));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student17','임윤아','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student17));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student18','김효연','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student18));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student19','최수영','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student19));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student20','권유리','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student20));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();

            sql = "insert into student_tbl values('student21','서주현','class03','teacher03',?)";
            bytes = getByteArrayFromDrawable(getDrawable(R.drawable.student21));
            p = sqLiteDatabase.compileStatement(sql);
            p.bindBlob(1,bytes);
            p.execute();


        }catch (Exception e){

        }


    }




    public byte[] getByteArrayFromDrawable(Drawable d){
        //이미지 저장하기

        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();

        return data;
    }


    public void getPhoto(ArrayList<Request> list){

        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("tidsnotedb",MODE_PRIVATE,null);
        Request r;
        Cursor c;
        byte[] result;

        for (int i=0;i<list.size();i++){

            r=list.get(i);


            c = sqLiteDatabase.rawQuery("SELECT * FROM student_tbl where s_id = '"+r.getStu_id()+"'", null);

            try {
                while (c.moveToNext()) {

                    result = c.getBlob(c.getColumnIndex("s_portrait"));
                    r.setPhoto(result);

                }


            }catch (Exception e){

            }finally {
                //sqLiteDatabase.close();
            }

        }




    }


    @Override
    public void setData(String str) {

        String new_intro[] = {"",""};
        new_intro[0]=t_id;
        new_intro[1]=str;
        send_Data("new_intro",new_intro);

    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    public String[] myLocation(){

        String[] location = new String[3];

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        gpsTracker = new GpsTracker(context);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        String address = getCurrentAddress(latitude, longitude);

        location[0] = Double.toString(latitude);
        location[1] = Double.toString(longitude);
        location[2] = address.toString();

        //return Double.toString(latitude)+", "+Double.toString(latitude)+", "+address.toString();
        return location;
    }

    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


}
