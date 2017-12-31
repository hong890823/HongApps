package com.hong.matrix.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.hong.matrix.R;

/**
 * Created by Hong on 2016/5/24.
 */
public class ImageMatrixView extends View {
    private Bitmap bitmap;
    private Matrix matrix;

    public ImageMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        setMatrix(new Matrix());
    }

    public void setMatrix(Matrix matrix){
        this.matrix = matrix;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawBitmap(bitmap,matrix,null);
    }

}
