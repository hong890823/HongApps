package com.hong.matrix.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.hong.matrix.R;

/**
 * Created by Hong on 2016/5/25.
 */
public class ImageXfermodeView extends View{
    private Bitmap bitmap;
    public ImageXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.iverson);
        bitmap = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        canvas.drawRoundRect(new RectF(0,0,bmp.getWidth(),bmp.getHeight()),50,50,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp,0,0,paint);
        paint.setXfermode(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth(),bitmap.getHeight());
    }

}
