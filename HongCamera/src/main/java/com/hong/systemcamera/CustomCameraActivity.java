package com.hong.systemcamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Hong on 2016/5/21.
 */
public class CustomCameraActivity extends Activity implements SurfaceHolder.Callback{
    private static final String TAG = "CustomCameraActivity";

    private SurfaceHolder holder;
    private Camera camera;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        SurfaceView preView = (SurfaceView)findViewById(R.id.preview);
        camera = getCamera();
        holder = preView.getHolder();
        holder.addCallback(this);

        path = getIntent().getStringExtra("path");
        preView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.autoFocus(null);//点击聚焦
            }
        });

        Toast.makeText(this,"共发现"+Camera.getNumberOfCameras()+"个摄像头",Toast.LENGTH_SHORT).show();
    }

    /*
     * 拍照的方法
     * */
    public void takePhoto(View view){
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(1920,1200);
        camera.setParameters(parameters);
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(success){
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {//该方法被调用的时候就会停止预览
                            try {
                                FileOutputStream fos = new FileOutputStream(path);
                                fos.write(data);
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent();
                            intent.putExtra("data",data);
                            setResult(RESULT_OK,intent);
                            CustomCameraActivity.this.finish();
                        }
                    });
                }
            }
        });

    }

    /*
     * 获取摄像头
     * */
    private Camera getCamera(){
        Camera camera;
        try {
            camera = Camera.open();//默认开启后置摄像头，如果传入参数可以开启指定摄像头。
            Camera.getNumberOfCameras();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /*
     * 开始预览
     * */
    private void startPreview(){
        if(camera==null)return;
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    /*
     * 释放相机资源
     * */
    private void realseCamera(){
        if(camera==null)return;
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        realseCamera();
    }

}
