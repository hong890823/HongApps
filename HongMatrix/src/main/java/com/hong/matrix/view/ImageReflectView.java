package com.hong.matrix.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.hong.matrix.R;

/**
 * Created by Hong on 2016/5/26.
 */
public class ImageReflectView extends View{
    private Bitmap srcBitmap,flectBitmap;
    private Paint paint;

    public ImageReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iverson);
        Matrix matrix = new Matrix();
        matrix.setScale(1,-1);
        flectBitmap = Bitmap.createBitmap(srcBitmap,0,0,srcBitmap.getWidth(),srcBitmap.getHeight(),matrix,true);
        paint = new Paint();
        paint.setShader( new LinearGradient(0,srcBitmap.getHeight(),0,(int)(srcBitmap.getHeight()*1.8),0XDD000666,0X10000888, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(srcBitmap,0,0,null);
        canvas.drawBitmap(flectBitmap,0,srcBitmap.getHeight(),null);
        canvas.drawRect(0,srcBitmap.getHeight(),srcBitmap.getWidth(),srcBitmap.getHeight()*2,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(srcBitmap.getWidth(),srcBitmap.getHeight()*2);
    }
}
