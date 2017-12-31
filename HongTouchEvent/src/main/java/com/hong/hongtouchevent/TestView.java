package com.hong.hongtouchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Hong on 2016/7/27.
 */
public class TestView extends TextView{

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TestActivity.TAG, "TestView-------dispatchTouchEvent"+"-------"+"执行");
        boolean result = super.dispatchTouchEvent(event);
        Log.e(TestActivity.TAG, "TestView-------dispatchTouchEvent"+"-------"+result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TestActivity.TAG, "TestView-------onTouchEvent"+"-------"+"执行");
        boolean result = super.onTouchEvent(event);
        Log.e(TestActivity.TAG, "TestView-------onTouchEvent"+"-------"+result);
        return result;
    }

}
