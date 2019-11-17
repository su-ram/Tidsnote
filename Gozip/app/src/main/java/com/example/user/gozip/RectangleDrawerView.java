package com.example.user.gozip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RectangleDrawerView extends View {



    private Paint mPaint;

    public RectangleDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Set up the pen: 10pt, no fill
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(10.0f);
        mPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(200, 400, 750, 1250, mPaint);
        canvas.drawRect(200, 1300, 750, 2100, mPaint);
        canvas.drawRect(800, 400, 1200, 950, mPaint);
        canvas.drawRect(800, 1000, 1200, 1500, mPaint);
        canvas.drawRect(800, 1550, 1200, 2100, mPaint);



    }


}
