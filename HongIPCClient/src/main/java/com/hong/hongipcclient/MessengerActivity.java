package com.hong.hongipcclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 用Messenger的方式进行跨进程通信
 * */
public class MessengerActivity extends Activity {
    Messenger messenger = null;
    boolean bound;
    private Button mBtn;
    private Messenger resultMessenger = new Messenger(new ResultHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        mBtn = (Button)findViewById(R.id.messenger_btn);
        mBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!bound){
                    return;
                }
                Message msg = Message.obtain(null, 1, 0, 0);
                try {
                    msg.replyTo = resultMessenger;
                    messenger.send(msg);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService();
    }

    private void bindService(){
        Intent intent = new Intent();
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            intent.setAction("com.hong.hongipcserver.MessengerService");
        }else{
            intent.setComponent(new ComponentName("com.hong.hongipcserver","com.hong.hongipcserver.MessengerService"));
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bound){
            unbindService(connection);
            bound = false;
        }

    }

    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
            bound =false;

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
        }
    };

    private class ResultHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            Toast.makeText(MessengerActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
