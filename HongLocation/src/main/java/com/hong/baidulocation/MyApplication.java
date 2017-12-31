package com.hong.baidulocation;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Hong on 2016/5/17.
 */
public class MyApplication extends Application{
    public LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        locationService = new LocationService(getApplicationContext());
        SDKInitializer.initialize(this);
    }


}
