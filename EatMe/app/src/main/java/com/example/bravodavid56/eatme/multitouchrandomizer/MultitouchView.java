package com.example.bravodavid56.eatme.multitouchrandomizer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class MultitouchView extends View {

    private static final int SIZE = 100;
    private static final String TAG = "MULTITOUCHVIEW";

    private SparseArray<PointF> mActivePointers;
    private Paint mPaint;
    private int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW };

    private Paint textPaint;

    long startTime;
    float angle;


    PointF winner;
    int winner_index;


    public MultitouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.startTime = System.currentTimeMillis();

        initView();
    }

    private void initView() {
        mActivePointers = new SparseArray<>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // set painter color to a color you like
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setTextSize(50);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {



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
            // start the timer
            startTime = System.currentTimeMillis();

            Log.e(TAG, "onTouchEvent: TOUCHED" );

        } else if (maskedAction == MotionEvent.ACTION_POINTER_DOWN) {
            PointF f = new PointF();
            f.x = event.getX(pointerIndex);
            f.y = event.getY(pointerIndex);
            mActivePointers.put(pointerId, f);


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
            startTime = System.currentTimeMillis();
            mActivePointers.removeAtRange(0, mActivePointers.size());
            winner = null;
        } else if (maskedAction == MotionEvent.ACTION_POINTER_UP) {
            // nothing
            startTime = System.currentTimeMillis();


        } else if (maskedAction == MotionEvent.ACTION_CANCEL) {
            mActivePointers.remove(pointerId);
            winner = null;

        }
        invalidate();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


            // draw all pointers
        if (winner == null) {
            for (int size = mActivePointers.size(), i = 0; i < size; i++) {

                PointF point = mActivePointers.valueAt(i);
                mPaint.setColor(colors[i % 9]);

                // this is the outer ring
                canvas.drawCircle(point.x, point.y, SIZE + 20, mPaint);
                mPaint.setColor(Color.WHITE);
                // this is the middle ring
                canvas.drawCircle(point.x, point.y, SIZE + 10, mPaint);
                mPaint.setColor(colors[i % 9]);
                // this is the inner ring
                canvas.drawCircle(point.x, point.y, SIZE, mPaint);
                mPaint.setColor(Color.WHITE);
                canvas.drawArc(point.x - 121, point.y - 121, point.x + 121, point.y + 121, (angle), 10, false, mPaint);
            }
        } else {
            mPaint.setColor(colors[winner_index]);
            canvas.drawPaint(mPaint);
            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(winner.x, winner.y, SIZE+30, mPaint);
            mPaint.setColor(colors[winner_index]);
            canvas.drawCircle(winner.x,winner.y,SIZE+20,mPaint);
            mPaint.setColor(Color.WHITE);
            canvas.drawRect(canvas.getWidth()/3, canvas.getHeight()/10,(canvas.getWidth()/3)*2, (canvas.getHeight()/10)+50, mPaint);
            textPaint.setColor(colors[winner_index]);
            textPaint.setTextSize(65);
            canvas.drawText(" W I N N E R",canvas.getWidth()/3, canvas.getHeight()/10+45, textPaint );

        }
        angle += 8;

        if (System.currentTimeMillis() - startTime > 5000) {
            if (winner == null) {
                Random random = new Random();
                int index = random.nextInt(mActivePointers.size());
                PointF point = mActivePointers.valueAt(index);
                winner = point;
                winner_index = index;

            }
        }


    }

}