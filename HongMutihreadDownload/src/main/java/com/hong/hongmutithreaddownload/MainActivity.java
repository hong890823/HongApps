package com.hong.hongmutithreaddownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    private static String imageURL = "http://bbsimg.ali213.net/data/attachment/forum/201306/17/14223167trb7trb6769gkz.jpg";
    private static String imageURL1 = "http://s2.nuomi.bdimg.com/upload/deal/2014/1/V_L/623682-1391756281052.jpg";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                File file = new File(Environment.getExternalStorageDirectory(),getFileName(imageURL));
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageShowView.setImageBitmap(bm);
            }

        }
    };
    private ImageView imageShowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageShowView = (ImageView) findViewById(R.id.image_show_view);

    }

    public void startDownLoad(View v){
        new Thread(new Download(imageURL,handler)).start();
    }

    /**
     * 获取下载文件名字的方法
     * */
    public String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }
}
