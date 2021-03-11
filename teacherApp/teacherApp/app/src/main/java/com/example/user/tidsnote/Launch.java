package com.example.user.tidsnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

public class Launch extends AppCompatActivity {

    IntentFilter[] readTag;
    String nfc_result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.launch);

        try{
            Thread.sleep(3000);

        }catch (Exception e){
            e.printStackTrace();

        }

        String id = TagedNfc();
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("student_id",id);
        startActivity(intent);
        finish();

    }

    public String TagedNfc() {

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("application/com.tidsnote2.example.android.nfc");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{ndef};
        readTag = nfcIntentFilter;


        // 인텐트를 통해서 nfc 신호를 수신한다.
        // rawMessages 변수에 nfc칩에 저장된 정보를 가져온다.
        // 원아 고유 id가 발견되면 nfc_result에 담아서 반환한다.

        if (getIntent().getAction() != null &&getIntent().getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            Parcelable[] rawMessages =
                    getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (rawMessages != null) {

                NdefMessage[] msg = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    msg[i] = (NdefMessage) rawMessages[i];
                }
                nfc_result = new String(msg[0].getRecords()[0].getPayload());
                if (nfc_result.charAt(0) == 's') {
                    String stu_id = nfc_result.substring(1);
                    if (stu_id.length()==1){
                        stu_id= "0"+stu_id;
                    }
                    nfc_result="student"+stu_id;

                }else{
                    nfc_result=null;
                }
            }
        }
        return nfc_result;
    }



}

