package com.hong.hongtouchevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 该项目旨在分析手指接触屏幕之后，从Activity层面开始分析touch事件的传递过程。
 * */
public class TestActivity extends AppCompatActivity {
    public static final String TAG = "HongTouchEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestView testView = (TestView)findViewById(R.id.test_view);
        testView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(TestActivity.this, "单击事件被执行", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "TestActivity-------dispatchTouchEvent"+"-------"+"执行");
        boolean result = super.dispatchTouchEvent(ev);
        Log.e(TAG, "TestActivity-------dispatchTouchEvent"+"-------"+result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "TestActivity-------onTouchEvent"+"-------"+"执行");
        boolean result =  super.onTouchEvent(event);
        Log.e(TAG, "TestActivity-------onTouchEvent"+"-------"+result);
        return result;
    }

}
