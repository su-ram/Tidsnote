package com.example.user.tidsnote;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationManager;
import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Date;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.app.Activity.RESULT_OK;

public class STT {


    private final int PERMISSION = 1;
    RecognitionProgressView recognitionProgressView;
    SpeechRecognizer mRecognizer;
    String stt=null;
    Context context;
    Activity activity;
    TextView textView;
    Intent intent;
    TabFragment1 fragment;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    Student student;
    int max=3;

    STT(Context c,Activity activity,Student s){

        this.activity=activity;
        this.context=c;



    }

    public void setComment(TabFragment1 tabFragment1){

        this.fragment=tabFragment1;

    }


    public SpeechRecognizer showSTTDialog(final Student student){

        this.student=student;
        final View view = (View) activity.getLayoutInflater().
                inflate(R.layout.stt_dialog, null);
        recognitionProgressView = (RecognitionProgressView)view.findViewById(R.id.stt_progress);
        textView= (TextView)view.findViewById(R.id.stt_txt);

        MaterialButton micbutton = (MaterialButton)view.findViewById(R.id.mic_button);
        MaterialButton sendbutton = (MaterialButton)view.findViewById(R.id.send_stt);

        builder = new AlertDialog.Builder(context);
        builder.setTitle(student.getStudent_name());
        builder.setView(view);
        dialog = builder.create();
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



                String classid = ((MainActivity)MainActivity.context).class_info.getC_id();


                if (student.getClass_id().equals(classid))
                    fragment.setComment(stt);


                mRecognizer.stopListening();
                if (stt != null && !stt.equals("")){

                    student.addStt_message(stt);
                    student.setDate(new Date());

                    Client client = new Client("add_stt");
                    client.setData(student);
                    client.start();


                    try{
                        client.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                dialog.dismiss();

            }
        });

        dialog.show();







        return mRecognizer;
    }



    private void startRecognition() {
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko");
        mRecognizer.startListening(intent);
    }




    public void setSTT(String str){

        textView.setText("\" "+ str+" \"");


    }
    public void STT(View view){



        if(Build.VERSION.SDK_INT >= 23){


            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO
            }, PERMISSION);
        }



        int[] colors = {
                ContextCompat.getColor(context, R.color.color1),
                ContextCompat.getColor(context, R.color.color2),
                ContextCompat.getColor(context, R.color.color3),
                ContextCompat.getColor(context, R.color.color4),
                ContextCompat.getColor(context, R.color.color5)
        };

        int[] heights = { 20, 54, 66, 43, 36 };


        mRecognizer=SpeechRecognizer.createSpeechRecognizer(context);
        recognitionProgressView.setSpeechRecognizer(mRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {






            @Override
            public void onError(int error) {
                String message;

                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        message = "오디오 에러";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        message = "클라이언트 에러";
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        message = "퍼미션 없음";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        message = "네트워크 에러";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        message = "네트웍 타임아웃";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        message = "찾을 수 없음";
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        message = "RECOGNIZER가 바쁨";
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        message = "서버가 이상함";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        message = "말하는 시간초과";
                        break;
                    default:
                        message = "알 수 없는 오류임";
                        break;
                }

            }

            @Override
            public void onResults(Bundle results) {

                ArrayList<String> matches =
                        results.getStringArrayList(
                                SpeechRecognizer.RESULTS_RECOGNITION);

                stt = matches.get(0);
                setSTT(stt);

                if (stt.equals("긴급")) {
                    final View alertview = (View) activity.getLayoutInflater().
                            inflate(R.layout.emergency, null);
                    dialog.setContentView(alertview);

                    final Emergency emergency = new Emergency(student,((MainActivity)MainActivity.context).t_id);
                    emergency.setActivity(activity);
                    emergency.setContext(context);

                    final TextView count = (TextView)alertview.findViewById(R.id.count_down);
                    TextView cancel = (TextView)alertview.findViewById(R.id.cancel_emergency);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    new CountDownTimer(3*1000, 1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            count.setText(Integer.toString(max--));
                        }

                        @Override
                        public void onFinish() {
                            emergency.CallEmergency();
                            dialog.dismiss();

                        }
                    }.start();
                }
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                super.onEvent(eventType, params);
                textView.setText("듣고 있습니다.");
            }

            @Override
            public void onBeginningOfSpeech() {
                super.onBeginningOfSpeech();
            }

            @Override
            public void onEndOfSpeech() {
                super.onEndOfSpeech();

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


    }





}
