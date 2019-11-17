package com.example.user.gozip;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.example.user.gozip.STT_NFC.setWindowFlag;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    private IntentFilter[] readTag;
    private final int PERMISSION = 1;
    private SpeechRecognizer mRecognizer;
    private RecognitionListener listener;
    private Intent sttIntent;

    private String[] STTmessage=new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.commentbtn);
        BottomAppBar bottomAppBar = (BottomAppBar)findViewById(R.id.bar);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        //Button btn2 = (Button)findViewById(R.id.camera);
        fullScreen();
        TagedNfc();






    }

    public void TagedNfc(){

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try{
            ndef.addDataType("application/com.gozip.example.android.nfc");
        }catch (IntentFilter.MalformedMimeTypeException e){

        }

        IntentFilter[] nfcIntentFilter = new IntentFilter[]{ndef};
        readTag=nfcIntentFilter;

        if (getIntent().getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {


            Parcelable[] rawMessages =
                    getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (rawMessages != null) {



                NdefMessage[] msg = new NdefMessage[rawMessages.length];

                for(int i=0;i<rawMessages.length;i++){
                    msg[i]=(NdefMessage)rawMessages[i];
                    //textView.append("\n"+msg[i].toString());
                }

                String result = new String(msg[0].getRecords()[0].getPayload());
                STTmessage[0]=result;
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                STT();



            }

        }





    }
    public void STT(){

        if(Build.VERSION.SDK_INT >= 23){

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO
            }, PERMISSION);
        }


        sttIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        listener=new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {

                ArrayList<String> matches =
                        results.getStringArrayList(
                                SpeechRecognizer.RESULTS_RECOGNITION);

                for(int i=0;i<matches.size();i++){
                    //edit2.setText(matches.get(i));
                    STTmessage[1]=matches.get(i);
                    //Toast.makeText(getApplicationContext(),stuID+","+message,Toast.LENGTH_SHORT).show();
                    if(STTmessage!=null){

                        SendToServer client = new SendToServer("STT",STTmessage);
                        client.start();
                    }


                }




            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };


        mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(sttIntent);



    }
    public void camera(View v){
        Intent intent = new Intent(MainActivity.this, roiCamera.class);
        startActivity(intent);

    }
    public void studentlist(View v){



        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){


                Intent intent = new Intent(MainActivity.this, studentlist.class);

                ArrayList<String> namelist = (ArrayList<String>) msg.obj;
                intent.putExtra("names", namelist);
                startActivity(intent);

                //Toast.makeText(getApplicationContext(), msg.toString(),Toast.LENGTH_LONG).show();


            }
        };

        SendToServer client = new SendToServer(handler,"getNames");
        client.start();


    }
    public void fullScreen(){
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
