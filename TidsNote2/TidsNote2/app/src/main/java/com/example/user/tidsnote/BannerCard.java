package com.example.user.tidsnote;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.material.card.MaterialCardView;

public class BannerCard extends MaterialCardView {

    Context context;
    boolean writing = true;

    public BannerCard(Context context) {
        super(context);
    }

    public BannerCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

    }

    public void setWriting(boolean writing) {
        this.writing = writing;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (writing)
            //글쓰는 중이면 가로챔
            return false;
        else
            return true;


    }

}
