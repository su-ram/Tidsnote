package com.example.user.tidsnote;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;


public class chattingTab extends Fragment {

    FloatingActionButton addPost_btn;
    private static final String TAG="ChattingdTab";
    private Activity activity;
    RecyclerView postListView;
    postDBAdapter helper;
    Button post_btn;
    PostListAdapter adapter;
    ArrayList<postData> postDataArrayList;
    ArrayList<Notice> mylist;
    FragmentActivity fragmentActivity;


    public PostListAdapter getAdapter(){
        return this.adapter;
    }
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof Activity){
            activity=(Activity)context;
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){

        Log.i("onCreateView","chattingTab");
        View view =inflater.inflate(R.layout.tab_chatting,container,false);
        View view1=inflater.inflate(R.layout.activity_posting,container,false);

        postListView=(RecyclerView) view.findViewById(R.id.mypostList);
        postDataArrayList=new ArrayList<postData>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        postListView.setLayoutManager(linearLayoutManager);

        adapter=new PostListAdapter(((MainActivity)getActivity()).mylist,getActivity());

        ((MainActivity)getActivity()).postListAdapter=this.adapter;
        postListView.setAdapter(adapter);
        fragmentActivity = getActivity();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==111){
                Toast.makeText(getContext(),"111",Toast.LENGTH_SHORT).show();
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

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final FloatingActionButton addPost_btn=activity.findViewById(R.id.addPost_btn);
        final Button post_btn=activity.findViewById(R.id.post_btn);
        RecyclerView listview=activity.findViewById(R.id.mypostList);


        addPost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,postingActivity.class);
                intent.putExtra("notice",new Notice());
                getActivity().startActivityForResult(intent,111);

            }
        });


/*
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
                alertDlg.setTitle("삭제");

                alertDlg.setPositiveButton( "예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which )
                    {
                        postDataArrayList.remove(position);
                      
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


                alertDlg.setMessage( String.format( "delete?"));
                alertDlg.show();
                return true;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(activity, postDetail.class);
                activity.startActivity(intent);

            }
        });

        */





    }






}
