package com.example.user.tidsnote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.view.View.VISIBLE;
import static com.example.user.tidsnote.R.id.menu_done;
import static com.example.user.tidsnote.R.id.menu_search;

public class InputForm extends AppCompatActivity {

    Toolbar toolbar;
    CardView cardView,cardView2;
    boolean writing=false;
    TabLayout tabLayout;
    myViewPager viewPager;
    TabFragment1 fragment1;
    TabFragment2 fragment2;
    TabFragment3 fragment3;
    TextView textView,badgetext;
    TabPagerAdapter pagerAdapter;
    NestedScrollView nestedScrollView;
    FloatingActionButton fab;
    LayoutInflater inflater;
    View tabView,tabView2;
    List<Fragment> fragmentList;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    Menu mymenu;
    Bundle bundle;
    int badgecount=10;
    int potision;
    Student student;
    StudentAdapter StudentAdapter;
    MainActivity mainActivity;
    String comment,sttmsg;
    HashMap<String,String> hashMap;
    private RecognitionListener listener;
    private Intent sttIntent;
    private final int PERMISSION = 1;
    ImageView imageView;
    SpeechRecognizer speechRecognizer;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);

        mainActivity=((MainActivity)MainActivity.context);


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager = (myViewPager) findViewById(R.id.viewpager);
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedscroll);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        imageView=(ImageView)findViewById(R.id.htab_header);



        nestedScrollView.setFillViewport(true);

        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tabView = inflater.inflate(R.layout.tab_notification_layout, null);
        tabView2=inflater.inflate(R.layout.tab_notification_layout_2,null);

        tabLayout.addTab(tabLayout.newTab().setText("코멘트"));
        tabLayout.addTab(tabLayout.newTab().setText("상태정보"));
        tabLayout.addTab(tabLayout.newTab().setText("기록"));

        //tabLayout.getTabAt(1).setCustomView(tabView2);
        tabLayout.getTabAt(2).setCustomView(tabView);

        TextView alert_badge = (TextView) tabView.findViewById(R.id.alert_badge);
        alert_badge.setVisibility(VISIBLE);

        Intent intent=new Intent(this.getIntent());
        student=(Student)intent.getSerializableExtra("Student");
        potision=student.getPos();
        getPhoto(student.getStudent_id());





        bundle = new Bundle();

        bundle.putString("comment",student.getComment());
        fragmentList=new ArrayList<>();

        fragment1 = new TabFragment1();
        fragment1.setArguments(bundle);


        fragment2 = new TabFragment2();

        bundle.putIntArray("status",student.getStatus());
        fragment2.setArguments(bundle);

        fragment3 = new TabFragment3();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);

        fragmentList.add(fragment3);


        send_Data("record_list",student);

        if (intent.getBooleanExtra("stt",false)) {


            STT start_STT = new STT((InputForm) this, this, student);
            speechRecognizer = start_STT.showSTTDialog(student);
            start_STT.setComment(fragment1);

        }


        toolbar.setTitle(student.getStudent_name());

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        /*
        viewPager.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){

            public void onSwipeTop() {
                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {//두번째 탭으로 스와이프 한 경우

                Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
                viewPager.setTouchevent(true);


            }
            public void onSwipeBottom() {
                Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
            }

        });
*/

        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragmentList,3);


        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            TextView tabname = (TextView) tabView.findViewById(R.id.tab_name);
            TextView tabname2 = (TextView)tabView2.findViewById(R.id.tab_name2);
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                switch (position){
                    case 0:
                        break;

                    case 1 :
                        showDone(false);
                        ((TabFragment1)pagerAdapter.getItem(0)).controlKeyboard();
                        appBarLayout.setExpanded(true);
                        tabname2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                        ((TextView)tabView2.findViewById(R.id.alert_badge2)).setVisibility(View.GONE); //빨간색 알람표시 끔

                        break;

                    case 2 :
                        appBarLayout.setExpanded(true);
                        showDone(false);
                        tabname.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                        ((TextView)tabView.findViewById(R.id.alert_badge)).setVisibility(View.GONE); //빨간색 알람표시 끔

                        break;
                }



               viewPager.setCurrentItem(position);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing

                int position = tab.getPosition();

                switch (position){
                    case 0:
                        //appBarLayout.setExpanded(true);
                        //showDone(true);

                        break;

                    case 1 :

                        //Toast.makeText(getApplicationContext(),"Expand",Toast.LENGTH_SHORT).show();

                        tabname2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        break;

                    case 2 :

                        //fragment1.hideKeyboard();
                        tabname.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        }) ;



        //((TabFragment2)pagerAdapter.getItem(1)).saveRadioButton(student.getStatus());





    }

    public void showDone(boolean show){

        if (show){
        mymenu.findItem(menu_done).getActionView().setVisibility(View.VISIBLE);}
        else{
            mymenu.findItem(menu_done).getActionView().setVisibility(View.GONE);
        }
    }

    public void setMenu(Menu m){
        mymenu=m;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.message_menu, menu);
        setMenu(menu);
        final Menu my_menu = menu;
        final MenuItem menuItem = my_menu.findItem(menu_search);

        badgetext = (TextView)menuItem.getActionView().findViewById(R.id.count_badge);
        //badgetext.setText("N");//카운트 갱신
        //badgetext.setVisibility(View.GONE); 알림이 0일 경우 보이지 않게함.

        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });






        my_menu.findItem(menu_done).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(my_menu.findItem(menu_done));
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final MenuItem menuItem = item;

        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작


                onBackPressed();


                return true;
            }
            case menu_search: {


                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(this).inflate(R.layout.message_from_parent,viewGroup,false);
                //TextView parent_message = (TextView)dialogView.findViewById(R.id.parent_message);
                //parent_message.setText("학부모의 메세지");

                TextView sttView = (TextView)dialogView.findViewById(R.id.notice);
                TextView curdate = (TextView)dialogView.findViewById(R.id.curdate);
                TextView parent_msg = (TextView)dialogView.findViewById(R.id.message_parent);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM / dd (E)");
                curdate.setText(simpleDateFormat.format(new Date()));

                ArrayList<String> stts = student.getStt_message();
                if (stts.size()!=0) {
                    String stt_text = stts.get(0);
                    for (int i = 1; i < stts.size(); i++) {

                        stt_text += "\n " + stts.get(i);

                    }

                    sttView.setText(stt_text);
                }
                parent_msg.setText(student.getParent_message());
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder
                       //.setTitle("가정에서 원으로")
                       .setView(dialogView)
                       .setCancelable(true);
               final AlertDialog dialog = builder.create();
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ((MaterialButton)dialogView.findViewById(R.id.okay)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });


                ((MaterialButton)dialogView.findViewById(R.id.more)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        viewPager.setCurrentItem(2);
                    }
                });


                dialog.getWindow().setGravity(Gravity.TOP|Gravity.RIGHT);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.show();
                dialog.getWindow().setLayout(1300,1500);

                ((TextView)menuItem.getActionView().findViewById(R.id.count_badge)).setVisibility(View.GONE);


                return true;

            }

            case menu_done:{
                viewPager.setPagingEnabled(true);
                ((TabFragment1)pagerAdapter.getItem(0)).hideKeyboard();
                item.getActionView().findViewById(R.id.menu_done).setVisibility(View.GONE);
                appBarLayout.setExpanded(true);
                //viewPager.setTouchevent(true);

                return true;
            }
            default :
                return super.onOptionsItemSelected(item) ;



        }

    }

    public void fabClicked(View v){


        onBackPressed();


    }
    public ArrayList<String> extractData(){

        ArrayList<String> result = new ArrayList<>();
        hashMap = ((TabFragment2)pagerAdapter.getItem(1)).getStatus();
        comment = ((TabFragment1)pagerAdapter.getItem(0)).getComment();

        result.add(comment);
        result.add(hashMap.get("health"));
        result.add(hashMap.get("meal"));
        result.add(hashMap.get("feeling"));
        result.add(Integer.toString(potision));

        for (int i =0 ; i<result.size();i++){
            Log.i("FUCK U",result.get(4));
        }

        //saveData(result);
        return result;
    }
    @Override
    public void onBackPressed() {

        ArrayList<String> data = extractData();
        Intent intent2 = new Intent();
        intent2.putExtra("result",data);

        ArrayList<String> newphotos = ((TabFragment2)pagerAdapter.getItem(1)).getPhotolist();
        for (int i = 0 ;i<newphotos.size();i++){
            Log.i("NEW+PHOTO",newphotos.get(i));
        }
        intent2.putExtra("uris",newphotos);

        if (checkStatus(hashMap,data.get(0))){
            //모두 입력한 경우
            Log.i("입력","일단 입력은 했따!!!!!!!!!!");

            if (checkUpdate(data)){
                //업데이트 한 경우
                Log.i("UPDATE","YES");
                setResult(1234,intent2);

            }else {
                Log.i("UPDATE", "NOPE");
                setResult(4444, intent2);
            }


        }else{
            //미입력

            if (hashMap.get("health").equals("-1") && hashMap.get("meal").equals("-1")&& hashMap.get("feeling").equals("-1") && comment.equals("comment"))
                setResult(910, intent2);
            else
                setResult(5678, intent2);


        }

        finish();

    }

    public boolean checkStatus(HashMap<String,String> hashMap,String comment){

        if (!hashMap.get("health").equals("-1") && !hashMap.get("meal").equals("-1") && !hashMap.get("feeling").equals("-1") && !comment.equals("") && !comment.equals("comment"))

            return true;
        else
            return false;


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            View v = getCurrentFocus();

            if (v instanceof EditText) {


                Rect outRect = new Rect();

                v.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {

                    v.clearFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }
            }

        }

        return super.dispatchTouchEvent( event );

    }


    public boolean checkUpdate(ArrayList<String> data){

        int[] status = student.getStatus();
        ArrayList<String> newphotos = ((TabFragment2)pagerAdapter.getItem(1)).getPhotolist();
        if (data.get(0).equals(student.getComment()) && data.get(1).equals(Integer.toString(status[0])) && data.get(2).equals(Integer.toString(status[1])) && data.get(3).equals(Integer.toString(status[2]))){
            Log.i("업데이트","변한게 일도 없음");
            if (newphotos.size()!=0){
                //사진추가
                return true;
            }
            return false;
        }else
            Log.i("업데이트","변한거 있음");

        return true;


    }

    public Bitmap getImage(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }


    public void getPhoto(String id){

        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("tidsnotedb",MODE_PRIVATE,null);



        byte[] result=null;

        Cursor c;
            c = sqLiteDatabase.rawQuery("SELECT * FROM student_tbl where s_id = '"+id+"'", null);
            while(c.moveToNext()){
                result =c.getBlob(c.getColumnIndex("s_portrait"));
                imageView.setImageBitmap(getImage(result));

        }



        if (result == null)
            Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_SHORT).show();



    }


    public void send_Data(String type, @Nullable Object obj){
        Log.i("실행순서","send_Data()");

        final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {

            }
        };

        Client client = new Client(fragment3,type);

        client.setPriority(Thread.MAX_PRIORITY);
        client.setData(obj);
        client.start();
        try{
            client.join();


            Log.i("Init","Finished");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (speechRecognizer != null){
            speechRecognizer.destroy();
            speechRecognizer=null;
        }



    }
}
