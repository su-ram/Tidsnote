package com.example.user.gozip;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.example.user.gozip.STT_NFC.setWindowFlag;

public class Comment extends AppCompatActivity {

    //Button namebtn = (Button)findViewById(R.id.namebtn);
    MaterialButton name;
    FloatingActionButton fab;
    String[] s;

    TextInputEditText comment;
    final String [] message = {"","","","",""};
    final RadioGroup[] radioGroup = new RadioGroup[3];
    final RadioButton radioButton=null;
    private SpeechRecognizer mRecognizer;
    private RecognitionListener listener;
    private Intent sttIntent;
    private final int PERMISSION = 1;
    String STTComment;
    Chip[] chips = new Chip[4];
    AlertDialog alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fullScreen();

        comment=(TextInputEditText)findViewById(R.id.commentmessage);
        comment.setText("");
        Intent intent=new Intent(this.getIntent());
        s=intent.getStringArrayExtra("name");
        name= (MaterialButton)findViewById(R.id.namebtn);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        name.setText(s[1]);
        message[0]=s[0];
        STTComment =s[2];

        if (!STTComment.equals("none")){
            comment.setText(STTComment);
        }
       //Toast.makeText(getApplicationContext(), s[s.length-1],Toast.LENGTH_SHORT).show();

        chips[0]=(Chip)findViewById(R.id.menu1);
        chips[1]=(Chip)findViewById(R.id.menu2);
        chips[2]=(Chip)findViewById(R.id.menu3);
        chips[3]=(Chip)findViewById(R.id.menu4);



        String [] menulist = s[s.length-1].split(",");
        for (int i=0;i<menulist.length;i++){
            chips[i].setText(menulist[i]);
        }


        ((RadioGroup)findViewById(R.id.feeling)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                message[1]=((RadioButton)findViewById(checkedId)).getText().toString();


            }
        });
        ((RadioGroup)findViewById(R.id.health)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                message[2]=((RadioButton)findViewById(checkedId)).getText().toString();

            }
        });

        ((RadioGroup)findViewById(R.id.meal)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                message[3]=((RadioButton)findViewById(checkedId)).getText().toString();
            }
        });



    }

    public void STT(View v){

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
                    comment.append(" "+matches.get(i));
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
    public void fab(View v){


        message[4]=comment.getText().toString().replaceAll("(\r|\n|\r\n|\n\r)"," ");

        if( message[1]!="" && message[2]!="" && message[3]!="" && comment.getText().toString()!=""){

            //Toast.makeText(getApplicationContext(),message[4],Toast.LENGTH_SHORT).show();
           SendToServer client = new SendToServer("sendData",message);
           client.start();

           //사진 전송 메시지 출력
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.icon);

            new AlertDialog.Builder(this)


                    .setMessage("\n\n\t입력 완료.\n\n")
                    //.setPositiveButton("확인", null)
                    .show();

            new Handler().postDelayed(new Runnable()
            {
                @Override     public void run()
                {
                    // 인텐트 호출
                    onBackPressed();
                }
            }, 1500);


        }else{
            Toast.makeText(getApplicationContext(),"입력이 완료되지 않았습니다.",Toast.LENGTH_SHORT).show();
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
