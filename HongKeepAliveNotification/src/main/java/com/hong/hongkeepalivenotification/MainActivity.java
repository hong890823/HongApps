package com.hong.hongkeepalivenotification;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static String ACTION = "com.hong.suibian";
    private MyBroadcastReceiver receiver;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            Toast.makeText(MainActivity.this, String.valueOf(i)+"---手机可用内存:"+getAvailMemory(), Toast.LENGTH_SHORT).show();
            i++;
            handler.sendEmptyMessageDelayed(i,10000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "onCreat方法执行", Toast.LENGTH_SHORT).show();

        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(receiver,filter);
    }

    public void startForegroundService(View view){
        Toast.makeText(MainActivity.this, "启动Service方法执行", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,ForegroundService.class);
        startService(intent);
    }


    public void startToast(View view){
        handler.sendEmptyMessageDelayed(0,10000);
    }

    public void startSecondActivity(View view){
        startActivity(new Intent(this,SecondActivity.class));
    }

    class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(ACTION.equals(action)){
                int i = intent.getIntExtra("i",0);
                Toast.makeText(MainActivity.this, String.valueOf(i)+"---手机可用内存:"+getAvailMemory(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取android当前可用内存大小
     * */
    private String getAvailMemory() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// 将获取的内存大小规格化
    }
}
