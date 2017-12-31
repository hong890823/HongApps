package com.hong.hongscratchcard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hong.hongscratchcard.R;

/**
 * Created by Hong on 2016/7/13.
 */
public class ScratchCardView extends View{
    private static final String TAG = "ScratchCardView";

    private Bitmap mBaseBitmap;//底图
    private Bitmap mMaskingBitmap;//蒙版图
    private Canvas mCanvas;//蒙版图画布
    private Paint mPaint;//蒙版图画笔
    private Path mPath;//蒙版图手指滑动路径
    private float mX;
    private float mY;

    public ScratchCardView(Context context) {
        this(context,null);
    }

    public ScratchCardView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ScratchCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        initPaint();
        mBaseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.base);
        mBaseBitmap =  Bitmap.createScaledBitmap(mBaseBitmap,1200,1920,true);
        mMaskingBitmap = Bitmap.createBitmap(1200,1920, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mMaskingBitmap);//蒙版图的canvas画布（操作该画布可以视为操作蒙版Bitmap）
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        mPath = new Path();
    }

    /**
     * 设置擦拭画笔，使擦拭效果更加圆滑平整
     * */
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(50);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
        protected void onDraw(Canvas canvas) {
        //个人理解想要最终展示在SratchCardView中的话必须要用onDraw()方法中的canvas来画才可以
        canvas.drawBitmap(mBaseBitmap,0,0,null);//画底图
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//设置Xfermode模式为DST_OUT
        mCanvas.drawPath(mPath,mPaint);//画手指划过的路径，综合Xfermode的DST_OUT模式可以擦去mCanvas之前画上的蒙版颜色
        canvas.drawBitmap(mMaskingBitmap,0,0,null);//将蒙版的效果显示出来

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                new Thread(runnable).start();
                break;
        }
        invalidate();
        return true;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int w = getWidth();
            int h = getHeight();

            int totalArea = w * h;
            int wrapArea = 0;
            int[] pixels = new int[totalArea];
            mMaskingBitmap.getPixels(pixels,0,w,0,0,w,h);

            for(int i=0;i<w;i++){
                for(int j=0;j<h;j++){
                    int index = i+j*w;
                    if(pixels[index]==0){
                        wrapArea++;
                    }
                }
            }

            if(wrapArea>0 && totalArea>0){
                int percent = (int)(wrapArea*100/totalArea);
                Log.e(TAG, "已擦除"+percent+"%");
            }


        }
    };

}
