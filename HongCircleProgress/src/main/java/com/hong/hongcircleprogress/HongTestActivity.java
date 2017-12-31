package com.hong.hongcircleprogress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Hong on 2016/7/12.
 */
public class HongTestActivity extends Activity{
    private HongTestView progressView;
    private Button loadBtn;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_progress);
        loadBtn = (Button)findViewById(R.id.hong_load);
        progressView = (HongTestView)findViewById(R.id.hong_progress);
    }

    public void startProgress(View view){
        progress=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress<100){
                    progress+=3;
                    progressView.startProgress(progress);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

    }

}
