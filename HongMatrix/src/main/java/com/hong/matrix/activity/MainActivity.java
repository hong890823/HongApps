package com.hong.matrix.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hong.matrix.R;
import com.hong.matrix.view.ImageBitmapMeshView;

/**
 * Created by Hong on 2016/5/25.
 */
public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchImageMatrix(View view){
        startActivity(new Intent(this,ImageMatrixActivity.class));
    }

    public void switchXfermode(View view){
        startActivity(new Intent(this,ImageXfermodeActivity.class));
    }

    public void switchBitmapShader(View view){
        startActivity(new Intent(this,ImageBitmapShaderActivity.class));
    }

    public void switchReflect(View view){
        startActivity(new Intent(this,ImageReflectActivity.class));
    }

    public void switchBitmapMesh(View view){
        startActivity(new Intent(this, ImageBitmapMeshActivity.class));
    }

}
