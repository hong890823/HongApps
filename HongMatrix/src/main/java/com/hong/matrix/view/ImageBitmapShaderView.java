package com.hong.matrix.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hong.matrix.R;

/**
 * Created by Hong on 2016/5/25.
 */
public class ImageBitmapShaderView extends View {
    private static String TAG = "ImageBitmapShaderView";

    private Bitmap bitmap;
    private Paint paint;
    public ImageBitmapShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iverson);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(bitmap.getWidth()/2,bitmap.getHeight()/2,bitmap.getHeight()/2,paint);//前两个参数是圆心点的位置
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth(),bitmap.getHeight());
    }

}
