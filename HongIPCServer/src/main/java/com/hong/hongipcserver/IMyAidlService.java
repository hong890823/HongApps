package com.hong.hongipcserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hong on 2016/6/9.
 * 用AIDL的方式进行通信
 */
public class IMyAidlService extends Service{
    private List<Person> persons;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        persons = new ArrayList<>();
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1+num2;
        }

        //Person类必须和AIDL文件夹中的Person同包名
        @Override
        public List<Person> addPerson(Person person) throws RemoteException {
            persons.add(person);
            return persons;
        }
    };
}
