package com.hong.hongtouchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Hong on 2016/7/27.
 */
public class TestViewGroup extends LinearLayout{

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TestActivity.TAG, "TestViewGroup-------dispatchTouchEvent"+"-------"+"执行");
        boolean result = super.dispatchTouchEvent(ev);
        Log.e(TestActivity.TAG, "TestViewGroup-------dispatchTouchEvent"+"-------"+result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TestActivity.TAG, "TestViewGroup-------onInterceptTouchEvent"+"-------"+"执行");
        boolean result = super.onInterceptTouchEvent(ev);
        Log.e(TestActivity.TAG, "TestViewGroup-------onInterceptTouchEvent"+"-------"+result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TestActivity.TAG, "TestViewGroup-------onTouchEvent"+"-------"+"执行");
        boolean result = super.onTouchEvent(event);
        Log.e(TestActivity.TAG, "TestViewGroup-------onTouchEvent"+"-------"+result);
        return result;
    }


}
