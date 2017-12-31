package com.hong.hongipcclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hong.hongipcserver.IMyAidlInterface;
import com.hong.hongipcserver.Person;

import java.util.List;

/**
 * MainActivity是用AIDL的方式进行通信的类
 * */
public class MainActivity extends AppCompatActivity {

    private EditText num1Edt,num2Edit;
    private TextView resultTv,personTv;
    private IMyAidlInterface iMyAidlInterface;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindService();
    }
    
    private void initView(){
        num1Edt = (EditText) findViewById(R.id.aidl_num1_edt);
        num2Edit = (EditText) findViewById(R.id.aidl_num2_edt);
        resultTv = (TextView) findViewById(R.id.aidl_result_tv);
        personTv = (TextView) findViewById(R.id.aidl_add_person_tv);
    }


    public void AidlCompute(View view){
        try {
            int num1 = Integer.valueOf(num1Edt.getText().toString());
            int num2 = Integer.valueOf(num2Edit.getText().toString());
            int result = iMyAidlInterface.add(num1,num2);
            resultTv.setText("AIDL远程调用的计算结果是："+result);
        } catch (RemoteException e) {
            e.printStackTrace();
            resultTv.setText("AIDL远程调用异常");
        }
    }

    public void AidlAddPerson(View view){
        Person person = new Person("影分身",1);
        try {
            List<Person> persons = iMyAidlInterface.addPerson(person);
            personTv.setText(persons.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
            personTv.setText("AIDL远程加人错误");
        }
    }

    public void switchToMessenger(View view){
        startActivity(new Intent(this,MessengerActivity.class));
    }

    private void bindService() {
        //5.0以上必须用显示的方式进行绑定服务，5.0以下则需要用隐式的方式进行绑定。
        Intent intent = new Intent();
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            intent.setAction("com.hong.hongipcserver.IMyAidlService");
        }else{
            intent.setComponent(new ComponentName("com.hong.hongipcserver","com.hong.hongipcserver.IMyAidlService"));

        }
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }


}
