package com.hong.hongipcserver;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by Hong on 2017/8/23.
 * Messenger的方式进行跨进程通信
 */

public class MessengerService extends Service {

    static final int HELLO_WORLD = 1;

    private static class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {//从客户端来的消息
            Message message = Message.obtain(msg);//要发给客户端的消息
            try {
                switch(msg.what){
                    case HELLO_WORLD:
                        String result = "Hello World!!!";
                        Bundle data = new Bundle();
                        data.putString("result",result);
                        message.setData(data);
                        /*
                        *注意这里可以这么玩，可以有一个Map变量把不同进程的messenger存储起来
                        * 然后可以随意分发，比如把A进程的处理结果交给B进程
                        * */
                        Messenger messenger = msg.replyTo;
                        if(messenger!=null)messenger.send(message);
                        break;
                    default:
                        super.handleMessage(msg);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    final Messenger messenger = new Messenger(new IncomingHandler());
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}

