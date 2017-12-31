package com.hong.hongmultidex;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Hong on 2016/10/10.
 * 可以把指定的类打入主的dex文件中，不过打包的过程不能用命令行，到CircleLibrary的时候会出错
 * 暂时绕了过去，先用的as自带的工具栏中的build打出了一个签名包测试的(不要带有混淆，带混淆的话指定分包失败)
 * 可以把指定的E.class打入classes.dex文件中
 *
 *运行Demo到模拟器的时候如果遇到啊哦问题可以尝试把
 File -> Settings -> Build, Execution, Deployment -> Instant Run.
 里面的第一个选项勾去掉
 */

public class HongApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
