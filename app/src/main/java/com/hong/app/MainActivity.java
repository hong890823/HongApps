package com.hong.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.test);
        socketThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setContent(ArrayList<String> content){
        Iterator<String> inter = content.iterator();
    }

    Thread socketThread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                StringBuilder sb = new StringBuilder("");
                URL url = new URL("http://www.baidu.com");
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String data = br.readLine();
                while (data != null) {
                    sb.append(data);
                    data = br.readLine();
                }
                br.close();
                isr.close();
                is.close();
                Log.e(TAG, sb.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    });

}
