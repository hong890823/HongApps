package com.hong.hongopengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GLSurfaceView glSurfaceView = (GLSurfaceView)findViewById(R.id.gl_surface_view);
        glSurfaceView.setRenderer(new GLDrawRender());

    }

}