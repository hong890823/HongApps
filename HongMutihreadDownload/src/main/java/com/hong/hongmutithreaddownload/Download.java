package com.hong.hongmutithreaddownload;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Hong on 2016/6/17.
 */
public class Download implements Runnable{
    private static final String TAG = "Download";
    private static final int THREAD_NUM = 3;

    private String url;
    private Handler handler;

    public Download(String url,Handler handler){
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        Log.e(TAG, "---开始下载---");
        downloadFile(url);
    }

    /**
     * 管理子线程下载文件的方法
     * */
    public void downloadFile(String url){
        try {
            Executor threadPool = Executors.newFixedThreadPool(THREAD_NUM);

            URL fileURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)fileURL.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setReadTimeout(3000);
            connection.setRequestMethod("GET");
            int length = connection.getContentLength();//获取将要下载的文件的总长度
            int block = length/THREAD_NUM;

            for(int i=0;i<THREAD_NUM;i++){
                long start = block*i;
                long end = block+(i+1)-1;
                if(i==THREAD_NUM-1)end = length;

                threadPool.execute(new DownLoadThread(start,end));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取下载文件名字的方法
     * */
    public String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }

    class DownLoadThread extends Thread{

        private long start;
        private long end;

        public DownLoadThread(long start,long end){
            this.start = start;
            this.end = end;
        }

        /**
         * 每个子线程具体下载文件的方法
         * */
        @Override
        public void run() {
            super.run();
            try {
                URL fileURL = new URL(url);
                HttpURLConnection connection = (HttpURLConnection)fileURL.openConnection();
                connection.setReadTimeout(3000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                //单个子线程请求的文件区间,也决定了该子线程所能下载的区间
                connection.setRequestProperty("Range","bytes="+start+"-"+end);
                connection.setRequestMethod("GET");
                InputStream is = connection.getInputStream();
                //新建一个可读可写可执行的RandomAccessFile文件
                RandomAccessFile file = new RandomAccessFile(new File(Environment.getExternalStorageDirectory(),getFileName(url)),"rwd");
                file.seek(start);
                byte[] buffer = new byte[1024*4];
                int len;
                while((len = is.read(buffer))!=-1){
                    file.write(buffer,0,len);
                }
                file.close();
                is.close();
                count++;
                Log.e(TAG, "---第"+count+"个线程完成下载---");
                if(count==THREAD_NUM){
                  handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    int count=0;
}
