package com.hong.ndklibrary;

/**
 * Created by Hong on 2016/6/3.
 */
public class NDKString {

    static{
       System.loadLibrary("NDKLibrary");
    }

    public static native String getFromC();

    //在SD卡的根目录中文件写入
    public native void uploadFile(String path);

    public native int[] updateIntArray(int[] array);
}
