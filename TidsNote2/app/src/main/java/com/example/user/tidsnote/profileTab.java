package com.example.user.tidsnote;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;


public class profileTab extends Fragment {


    View view;
    MaterialButton intro,intro_done;
    TextInputEditText textInputEditText;
    Class_ c;
    TextView age, sum, t_name,t_pnum,class_name;
    ScrollView scrollView;
    final int MIN_KEYBOARD_HEIGHT_PX = 150;
    FragmentListener fragmentListener;

    // Top-level window decor view.
    final View decorView = ((MainActivity)MainActivity.context).getWindow().getDecorView();


    public profileTab() {
        // Required empty public constructor
    }


   public void setClass_(Class_ val){
        this.c=val;
   }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof FragmentListener){
            fragmentListener = (FragmentListener)context;

        }else{
            throw new RuntimeException(context.toString()+" must implements interface.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        textInputEditText.setFocusable(false);
        textInputEditText.setClickable(false);
        textInputEditText.setCursorVisible(false);
        textInputEditText.setFocusedByDefault(false);
        textInputEditText.setFocusableInTouchMode(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.tab_profile, container, false);

        intro=(MaterialButton)view.findViewById(R.id.edit_intro);
        scrollView=(ScrollView)view.findViewById(R.id.profile_scroll);



/*
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                final Rect windowVisibleDisplayFrame = new Rect();
                int lastVisibleDecorViewHeight =0 ;

                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                    final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

                    // Decide whether keyboard is visible from changing decor view height.
                    if (lastVisibleDecorViewHeight != 0) {
                        if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                            // Calculate current keyboard height (this includes also navigation bar height when in fullscreen mode).
                            int currentKeyboardHeight = decorView.getHeight() - windowVisibleDisplayFrame.bottom;
                            // Notify listener about keyboard being shown.
                            //listener.onKeyboardShown(currentKeyboardHeight);
                            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



                        } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                            // Notify listener about keyboard being hidden.
                            //listener.onKeyboardHidden();
                        }
                    }
                    // Save current decor view height for the next call.
                    lastVisibleDecorViewHeight = visibleDecorViewHeight;
                }




        });
*/
        textInputEditText=(TextInputEditText)view.findViewById(R.id.profile_text);
        textInputEditText.setFocusable(false);
        textInputEditText.setClickable(false);
        textInputEditText.setCursorVisible(false);
        textInputEditText.setFocusedByDefault(false);
        textInputEditText.setFocusableInTouchMode(false);

        age = (TextView)view.findViewById(R.id.class_age);
        sum = (TextView)view.findViewById(R.id.class_sum);
        t_name = (TextView)view.findViewById(R.id.class_t_name);
        t_pnum=(TextView)view.findViewById(R.id.t_pnum);
        class_name=(TextView)view.findViewById(R.id.class_name);


        age.setText(Integer.toString(c.getAge())+"세");
        sum.setText(Integer.toString(c.getSum())+"명");
        t_name.setText(c.getT_name());
        textInputEditText.setText(c.getIntro());
        class_name.setText(c.getC_name()+"반");
        t_pnum.setText(((MainActivity)MainActivity.context).t_pnum);






        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((intro.getText().toString()).equals("수정")) {//수정 시작



                    //scrollView.post(new Runnable() { @Override public void run() { scrollView.smoothScrollBy(0, 800); } });


                    textInputEditText.setFocusable(true);
                    textInputEditText.setClickable(true);

                    textInputEditText.setCursorVisible(true);
                    textInputEditText.setFocusedByDefault(true);
                    textInputEditText.setFocusableInTouchMode(true);
                    textInputEditText.setSelection(textInputEditText.length());

                    textInputEditText.requestFocus();

                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


                    intro.setText("완료");

                }else{//수정 완료

                    String new_intro = textInputEditText.getText().toString();
                    fragmentListener.setData(new_intro);
                    textInputEditText.setFocusable(false);
                    textInputEditText.setClickable(false);
                    textInputEditText.setCursorVisible(false);
                    textInputEditText.setFocusedByDefault(false);
                    textInputEditText.setFocusableInTouchMode(false);

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textInputEditText.getWindowToken(), 0);


                    intro.setText("수정");





                }
            }
        });






        return view;
    }






}
