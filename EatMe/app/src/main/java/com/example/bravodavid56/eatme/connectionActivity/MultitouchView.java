package com.example.bravodavid56.eatme.connectionActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

public class MultitouchView extends View {

    private static final int SIZE = 60;

    private SparseArray<PointF> mActivePointers;
    private Paint mPaint;
    private int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW };

    private Paint textPaint;


    public MultitouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mActivePointers = new SparseArray<PointF>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // set painter color to a color you like
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e("TAG", "onTouchEvent: "+ event.getPointerCount() );

        if (event.getPointerCount() == 1) {
            Log.e("TAG", "onTouchEvent: Single Touch event" );
        }
        else if (event.getPointerCount() > 1) {
            Log.e("TAG", "onTouchEvent: Multitouch event" );
        }
        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        if (maskedAction == MotionEvent.ACTION_DOWN) {
            // This triggers when there is only one pointer on the screen
            PointF f = new PointF();
            f.x = event.getX(pointerIndex);
            f.y = event.getY(pointerIndex);
            mActivePointers.put(pointerId, f);

        } else if (maskedAction == MotionEvent.ACTION_POINTER_DOWN) {
            PointF f = new PointF();
            f.x = event.getX(pointerIndex);
            f.y = event.getY(pointerIndex);
            mActivePointers.put(pointerId, f);
            Log.e("TAG", "onTouchEvent: YOU TOUCHED THE SCREEN" );
        } else if (maskedAction == MotionEvent.ACTION_MOVE) {
            for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                PointF point = mActivePointers.get(event.getPointerId(i));
                if (point != null) {
                    point.x = event.getX(i);
                    point.y = event.getY(i);
                }
            }
        } else if (maskedAction == MotionEvent.ACTION_UP) {
            // nothing
        } else if (maskedAction == MotionEvent.ACTION_POINTER_UP) {
            // nothing
        } else if (maskedAction == MotionEvent.ACTION_CANCEL) {
            mActivePointers.remove(pointerId);
        }
        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setTextSize(50);
        // draw all pointers
        for (int size = mActivePointers.size(), i = 0; i < size; i++) {
            PointF point = mActivePointers.valueAt(i);
            if (point != null)
                mPaint.setColor(colors[i % 9]);
            canvas.drawCircle(point.x, point.y, SIZE, mPaint);
//            canvas.drawCircle(point.x, point.y, SIZE, mPaint);
            mPaint.setColor(colors[i % 9] +1);
            canvas.drawText(("Player " + i % 9), 0, 8,point.x, point.y, mPaint);


        }
        canvas.drawText("Total pointers: " + mActivePointers.size(), 10, 40 , textPaint);
    }

}