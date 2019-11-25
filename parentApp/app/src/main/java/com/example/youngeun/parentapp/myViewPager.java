package com.example.youngeun.parentapp;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

public class myViewPager extends ViewPager {

    boolean enabled = true;
    boolean touchevent = false;
    Context context;
    Activity activity;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;


    public myViewPager(Context c) {
        super(c);
        this.context=c;
    }

    public myViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
