package com.example.youngeun.parentapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    private WriteFragment writeFragment=new WriteFragment();
    private ReadFragment readFragment=new ReadFragment();
    private MessageTab messageTab;
    private RequestTab requestTab;
    Toolbar toolbar;
    Button res_btn;
    MenuInflater menuInflater;
    Menu menu;
    MenuItem menuItem;
    public ArrayList<messageData> messageList=new ArrayList<>();
    ArrayList<CommentRequest> requestList=new ArrayList<>();
    ArrayList<String> midList=new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    private String s_id,p_id;
    private SharedPreferences sharedPreferences;
    public static final String PREFERENCE= "preference";
    public static final String PREF_NAME = "name";
    public static final String PREF_PASSWD = "passwd";



    //ArrayList<Comment> commentArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent rcv_intent=getIntent();

        s_id=rcv_intent.getExtras().getString("studentID");
        p_id=rcv_intent.getExtras().getString("parentID");

        myIDs.s_id=s_id;
        myIDs.p_id=p_id;
        Log.i("MainActivty: ",s_id);



//        sharedPreferences=getSharedPreferences(PREFERENCE,Context.MODE_PRIVATE);


        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



        CommentRequest newInit=new CommentRequest();
        newInit.setS_id(s_id);
        newInit.setC_date("");

        requestList.add(newInit);
        send_Data("get_message",s_id);

        //send_Data("get_mymids",p_id);
        initArrayList();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, readFragment).commitAllowingStateLoss();


        //recyclerViewAdapter= new RecyclerViewAdapter(messageList);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String deviceToken = instanceIdResult.getToken();
                // Do whatever you want with your token now
                // i.e. store it on SharedPreferences or DB
                // or directly send it to server

                Log.i("IDService", "device token : " + deviceToken);


            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        if(recyclerViewAdapter!=null){
            recyclerViewAdapter.notifyDataSetChanged();
        }

    }

    public String getS_id() {
        return s_id;
    }


    public String getP_id() {
        return p_id;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new ReadFragment();

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            toolbar.setTitle("원아수첩");

                            selectedFragment = readFragment;
                            break;
                        case R.id.nav_comment:
                            toolbar.setTitle("가정에서의 소식");
                            selectedFragment = writeFragment;
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commitAllowingStateLoss();
                    return true;
                }
            };

    @Override
    protected void onActivityResult(final int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        ArrayList<String> result;

        if(requestCode==111){

            if(data!=null){
                messageData newMessage=(messageData)data.getSerializableExtra("message");
                if(newMessage!=null){
                    if(newMessage.getState().equals("final")){
                        messageList.add(0,newMessage);
                    }
                    send_Data2("send_message",newMessage);

                }}
        }
    }






    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

    }


    public void send_Data(String type, @Nullable String obj) {


        Client client = new Client(this, type);
        client.setData(obj);
        client.start();
        try {
            client.join();
            Log.i("Init", "Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void initArrayList(){

        //messageList=new ArrayList<>();

        writeFragment.messageDataArrayList=this.messageList;

    }

    public void send_Data2(String type, @Nullable Object obj) {

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };

        Client client = new Client(this, type);
        client.setData(obj);
        client.start();
        try {
            client.join();
            Log.i("Init", "Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
