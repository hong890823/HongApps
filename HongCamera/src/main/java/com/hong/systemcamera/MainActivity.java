package com.hong.systemcamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 包含图像RGBA的处理（处理色调，饱和度，明度）以及处理成底片的方法
 * */
public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private static int REQ_1 = 1;
    private static int REQ_2 = 2;
    private static int REQ_3 = 3;
    private static int MAX_VALUE = 255;
    private static int MID_VALUE = 127;
    private static int FLAG;
    private ImageView showImv;
    private String path;
    private Uri uri;
    private Bitmap bitmap;
    private float hum,saturation,lum;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showImv = (ImageView) findViewById(R.id.camera_show);
        SeekBar humBar = (SeekBar)findViewById(R.id.image_hum);
        SeekBar saturationBar = (SeekBar)findViewById(R.id.image_saturation);
        SeekBar lumBar = (SeekBar)findViewById(R.id.image_lum);

        humBar.setOnSeekBarChangeListener(this);
        saturationBar.setOnSeekBarChangeListener(this);
        lumBar.setOnSeekBarChangeListener(this);
        humBar.setProgress(MID_VALUE);
        saturationBar.setProgress(MID_VALUE);
        lumBar.setProgress(MID_VALUE);
        humBar.setMax(MAX_VALUE);
        saturationBar.setMax(MAX_VALUE);
        lumBar.setMax(MAX_VALUE);

        path = Environment.getExternalStorageDirectory()+"/Hong.png";
        uri = Uri.fromFile(new File(path));
    }

    public void startCamera(View view){
        switch(view.getId()){
            case R.id.camera_start1:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//隐式跳转
                startActivityForResult(intent1,REQ_1);
                break;
            case R.id.camera_start2:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//隐式跳转
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(intent2,REQ_2);
                break;
            case R.id.camera_start3:
                Intent intent3 = new Intent(MainActivity.this,CustomCameraActivity.class);
                intent3.putExtra("path",path);
                startActivityForResult(intent3,REQ_3);
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==REQ_1){
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap)bundle.get("data");//因为Bitmap类实现了Parcelabel接口
                showImv.setImageBitmap(bitmap);//这种方式获取的图片其实是缩略图，像素不高。
                FLAG = 0;
            }

            if(requestCode==REQ_2){
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(fis);
                showImv.setImageBitmap(bitmap);
                FLAG = 1;
            }

            if(requestCode==REQ_3){
                byte[] bytes = data.getByteArrayExtra("data");
                bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                //180度旋转照片
                Matrix matrix = new Matrix();
//                matrix.setRotate(180);//set方式不会多效果并存，post可以多效果并存
                bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
                showImv.setImageBitmap(bitmap);
                FLAG = 2;
            }

        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch(seekBar.getId()){
            case R.id.image_hum:
                hum = (progress-MID_VALUE)*1.0F/MID_VALUE*180;
                break;
            case R.id.image_saturation:
                saturation = progress*1.0F/MID_VALUE;
                break;
            case R.id.image_lum:
                lum =  progress*1.0F/MID_VALUE;
                break;
        }

        if(bitmap!=null && FLAG==0){
            showImv.setImageBitmap(ImageHelper.handleImageEffect(bitmap,hum,saturation,lum));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(bitmap!=null && FLAG!=0){
            showImv.setImageBitmap(ImageHelper.handleImageEffect(bitmap,hum,saturation,lum));//处理图像ARGB生成新的特效图像
        }
    }

    public void imageNative(View view){
        if(bitmap!=null){
            showImv.setImageBitmap(ImageHelper.handleImageNative(bitmap));//处理图像像素点ARGB生成新的底片图像
        }
    }

}
