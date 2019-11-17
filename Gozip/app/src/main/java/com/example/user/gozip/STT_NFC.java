package com.example.user.gozip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class STT_NFC extends AppCompatActivity {

    private EditText edit1, edit2;
    private FloatingActionButton fab;
    private IntentFilter[] readTag;
    private SpeechRecognizer mRecognizer;
    private RecognitionListener listener;
    private Intent sttIntent;
    private final int PERMISSION = 1;
    private CoordinatorLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullScreen();
        setComponent();
        TagedNfc();




    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    private void setComponent(){

        //edit1=(EditText)findViewById(R.id.edit1);
        //edit2=(EditText)findViewById(R.id.edit2);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        layout=findViewById(R.id.layout);


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

                }

                String result = new String(msg[0].getRecords()[0].getPayload());

                edit1.setText(result);
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
                    edit2.setText(matches.get(i));
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
    public void onClick(View view){

        String id = edit1.getText().toString();
        String comment = edit2.getText().toString();

        if(!id.equals("") && !comment.equals("")){

            Client client= new Client(id,comment);
           // client.start();
           Toast toast = Toast.makeText(getApplicationContext(), "전송되었습니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0,700);
                    toast.show();

        }



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
