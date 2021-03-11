package com.example.user.tidsnote;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class TabFragment1 extends Fragment{

    private View view;
    Context context;
    String comment="NULL";
    TextInputEditText editText;
    TextInputLayout textInputLayout;
    AppBarLayout appBarLayout;
    MenuItem menuItem;
    View mainView;
    static final int MIN_DISTANCE = 150;
    GestureDetector gestureDetector;
    private Activity activity;
    SimpleGestureFilter detector;
    private GestureDetectorCompat mDetector;
    String comment_bf="previous_comment";

    @Override
    public void setArguments(@Nullable Bundle args) {
        //super.setArguments(args);

        this.comment_bf=args.getString("comment");

    }

    public void setEditText(boolean val){

        editText.setFocusable(val);
        editText.setFocusableInTouchMode(val);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        mainView = inflater.inflate(R.layout.activity_input_form,container,false);
        textInputLayout=(TextInputLayout)view.findViewById(R.id.textinputlayout);
        editText=(TextInputEditText)view.findViewById(R.id.textinput);

        setEditText(true);

        Log.i("onCreate comment_string",comment_bf);
        /*memo icon code start*/
        setMemo(comment_bf);

        if(comment_bf.contains("[메모]")) {
            SpannableStringBuilder builder=setMemo(comment_bf);

/*
            int count=getCharCount(comment_bf,"[메모]");
            int start = comment_bf.indexOf("[메모]");
            int end=0;
            String temp_string="";

            temp_string=comment_bf;

            Drawable dr = context.getResources().getDrawable(R.drawable.memo);
            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
            SpannableStringBuilder builder = new SpannableStringBuilder(comment_bf);
           // ImageSpan span = new ImageSpan(dr,ImageSpan.ALIGN_BOTTOM);

            Log.i("getCharCount:",String.valueOf(count));

             while(temp_string.contains("[메모]")){
                if (start > -1) {

                    end =start + "[메모]".length();

                    Log.i("start and end:",String.valueOf(start)+"/"+String.valueOf(end));

                    builder.setSpan(new ImageSpan(dr,ImageSpan.ALIGN_BOTTOM), start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

 }

                    temp_string=comment_bf.substring(end);
                    start=temp_string.indexOf("[메모]")+end;

*/
            editText.setText(builder);
            editText.setSelection(builder.length());

            Log.i("builder",builder.toString());

        }


        /*meno icon code end*/

        else{
            editText.setText(comment_bf);
            editText.setSelection(editText.length());

        }



/*
        editText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Toast.makeText(getActivity(), "EditText Touched"+Integer.toString(i++), Toast.LENGTH_SHORT).show();
                //v.getParent().requestDisallowInterceptTouchEvent(! ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP));

                //editText.getParent().requestDisallowInterceptTouchEvent(false);
                //v.getParent().requestDisallowInterceptTouchEvent(true);
                   // ((InputForm)getActivity()).viewPager.setPagingEnabled(true);
                //Toast.makeText(getContext(),"EditText Touched",Toast.LENGTH_SHORT).show();

                ((InputForm)getActivity()).appBarLayout.setExpanded(false,true);
                    ((InputForm)getActivity()).showDone(true);
                    setEditText(true);

                return false;
            }
        });*/

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputForm)getActivity()).viewPager.setPagingEnabled(false);
                ((InputForm)getActivity()).appBarLayout.setExpanded(false,true);
                ((InputForm)getActivity()).showDone(true);
                setEditText(true);
            }
        });


        return view;

    }

    public TextInputEditText getEditText(){
        return editText;
    }

    public void controlKeyboard(){
        hideKeyboard();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }


    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);


    }
    public String getComment(){
        comment=editText.getText().toString();
        return comment;

    }

    public void setComment(String str){
        if (editText != null) {
            if ((editText.getText().toString()).equals("comment")){
                if(str.length()<2){
                    editText.setText("[음성기록] " + str+"\n");
                }else{

                String front_str=str.substring(0,2);

                if(front_str.equals("메모")){
                    String input="[메모]"+str.substring(3)+"\n";
                    SpannableStringBuilder builder=setMemo(input);
                    editText.setText(builder);
                }else {
                    editText.setText("[음성기록] " + str+"\n");
                }}
            }else {

                if(str.length()<2) {
                    String input=editText.getText().toString() + "\n[음성기록] " + str;
                    if(input.contains("[메모]")){
                        SpannableStringBuilder builder=setMemo(input);
                        editText.setText(builder);
                    }else{
                        editText.setText(editText.getText().toString() + "\n[음성기록] " + str);
                    }
                }else {

                    String front_str=str.substring(0,2);

                if(front_str.equals("메모")){
                    String input=editText.getText().toString()+"\n[메모]"+str.substring(3);
                    SpannableStringBuilder builder=setMemo(input);
                    editText.setText(builder);

                }else {
                    String input=editText.getText().toString() + "\n[음성기록] " + str;
                    if(input.contains("[메모]")){
                        SpannableStringBuilder builder=setMemo(input);
                        editText.setText(builder);
                    }else{
                        editText.setText(editText.getText().toString() + "\n[음성기록] " + str);
                    }}}
            }

            editText.setSelection(editText.length());

        }
    }

    public int getCharCount(String origin,String memo){

        int count=0;

        String[] div=origin.split(memo);
        Log.i("origin.split('memo'):",div[0]+"/"+div[1]);
        int len=div.length;

        for(int i=0;i<len;i++){
            if(memo.equals(div[i])){
                count++;
            }
        }

        return count;
    }

    public SpannableStringBuilder setMemo(String input) {
        if (input.contains("[메모]")) {

            int start = input.indexOf("[메모]");
            int end = 0;
            String temp_string = "";

            temp_string = input;

            Drawable dr = context.getResources().getDrawable(R.drawable.memo);
            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
            SpannableStringBuilder builder = new SpannableStringBuilder(input);
            // ImageSpan span = new ImageSpan(dr,ImageSpan.ALIGN_BOTTOM);


            while (temp_string.contains("[메모]")) {
                if (start > -1) {

                    end = start + "[메모]".length();

                    Log.i("start and end:", String.valueOf(start) + "/" + String.valueOf(end));

                    builder.setSpan(new ImageSpan(dr, ImageSpan.ALIGN_BOTTOM), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }

                temp_string = input.substring(end);
                start = temp_string.indexOf("[메모]") + end;


            }

            return builder;
        }else{
            SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder();
            return spannableStringBuilder;
        }
    }


}