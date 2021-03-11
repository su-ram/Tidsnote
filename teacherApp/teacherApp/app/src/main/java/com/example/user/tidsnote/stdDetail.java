package com.example.user.tidsnote;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class stdDetail extends AppCompatActivity {


    EditText name_view,class_view,age_view,pnum_view,memo_view;
    Button std_mod,std_del;
    myDBAdapter helper;
    StdListAdapter adapter;
    studentData studentdata;
    ArrayList<studentData> studentDataArrayList;
    int selectedPos;
    private Activity activity;





        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stddetail);

        Bundle b=new Bundle();
        final int value= b.getInt("id",0);
        helper = new myDBAdapter(getApplicationContext());
        name_view=findViewById(R.id.name_view);
        class_view=findViewById(R.id.class_view);
        age_view=findViewById(R.id.age_view);
        pnum_view=findViewById(R.id.pnum_view);
        memo_view=findViewById(R.id.memo_view);

        std_mod=findViewById(R.id.std_mod);
        std_del=findViewById(R.id.std_del);



        studentDataArrayList= new ArrayList<studentData>();

        final ArrayList<String> name_bf=helper.getName();
        final ArrayList<String> class_bf=helper.getClasses();

        name_view.setText(name_bf.get(1));
        class_view.setText(class_bf.get(1));

        /*back2stdlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(stdDetail.this,studentTab.class);
                startActivity(intent);
                finish();
            }
        });
*/
        std_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                alertDlg.setTitle("삭제");

                alertDlg.setPositiveButton( "예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which )
                    {
                       studentDataArrayList.remove(value);
                        // 아래 method를 호출하지 않을 경우, 삭제된 item이 화면에 계속 보여진다.
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();  // AlertDialog를 닫는다.



                    }
                });

                // '아니오' 버튼이 클릭되면
                alertDlg.setNegativeButton( "아니오", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        dialog.dismiss();  // AlertDialog를 닫는다.
                    }
                });


                alertDlg.setMessage( "delete?");
                alertDlg.show();



            }
        });


        std_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                alertDlg.setTitle("수정");

                alertDlg.setPositiveButton( "예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which )
                    {
                     /*   items.remove();

                        // 아래 method를 호출하지 않을 경우, 삭제된 item이 화면에 계속 보여진다.
                        adapter.notifyDataSetChanged();*/
                        dialog.dismiss();  // AlertDialog를 닫는다.

                    }
                });

                // '아니오' 버튼이 클릭되면
                alertDlg.setNegativeButton( "아니오", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        dialog.dismiss();  // AlertDialog를 닫는다.
                    }
                });


                alertDlg.setMessage( "modify?");
                alertDlg.show();



            }
        });

    }



}
