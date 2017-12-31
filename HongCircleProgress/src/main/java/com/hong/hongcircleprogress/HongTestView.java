package com.hong.hongcircleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Hong on 2016/7/12.
 */
public class HongTestView extends View {
    private static final String TAG = "HongTestView";

    public static final int STROKE = 0;
    public static final int FILL = 1;

    private Paint paint;
    private int roundWidth;//圆环的宽度
    private int radius;//画的圆的半径
    private int max;
    private int progress;
    private int style;//进度的风格，实心或者空心

    public HongTestView(Context context) {
        this(context,null);
    }

    public HongTestView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }//之前不小心写成了引用this(context,null,0)这样的构造函数，导致布局文件中该控件可以显示出来，但是在Activity中获取实例为null的问题。

    public HongTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        roundWidth = 50;
        radius = 100;
        max = 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth()/2;//获取圆心的x坐标
        int cy = getHeight()/2;//获取圆心的y坐标

        drawRound(cx,cy,canvas);
        drawProgress(cx,cy,canvas,0);
    }

    /**
     * 环形图绘制
     * @param cx 圆心的x坐标
     * @param cy 圆心的y坐标
     *
     * */
    private void drawRound(int cx,int cy,Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);//设置空心风格
        /*
        setStrokeWidth需要和空心风格一起使用，如果是实心风格的话，那么该方法起不到什么作用，最终决定图画大小的仍是drawCircle方法中的半径参数。
        setStrokeWidth方法和空心风格一起使用的话可以画出环形图的效果，该方法决定的是画笔的宽度。举个例子说，前提是空心的风格下，如果drawCircle方法中
        半径参数是100，那么setStrokeWidthf方法中的参数是200的话，恰好可以把这个空心圆填满成半径两倍大的实心圆，因为该画笔向内向外都扩展了100的长度，
        而向内的100正好把里面的空心都填满了。
         **/
        paint.setStrokeWidth(roundWidth);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);  //消除锯齿
        /*
        * cx,cy是圆心的坐标，该坐标相对于该View而言（不相对于屏幕，不相对于ViewGroup容器），无论在布局文件中把该View放到哪里，通过getWidth()和
        * getHeight()方法获得的cx，cy的值都是一样的。
        * */
        canvas.drawCircle(cx,cy,radius,paint);
        Log.e(TAG, "onDraw: cx:"+cx+"---cy:"+cy );
    }

    /**
     * 弧形绘制出进度条
     * @param cx 圆心的x坐标
     * @param cy 圆心的y坐标
     * */
    private void drawProgress(int cx,int cy,Canvas canvas,int style){
        if(progress==0)return;
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(roundWidth);
        RectF oval = new RectF(cx - radius, cy - radius, cx
                + radius, cy + radius);//相当于圆弧的一个内接矩形

        switch (style) {
            case STROKE:{
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 0, 360 * progress / max, false, paint);  //根据进度空心弧
                break;
            }
            case FILL:{
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawArc(oval, 0, 360 * progress / max, true, paint);  //根据进度画圆弧
                break;
            }
        }
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param progress
     */
    public synchronized void startProgress(int progress){
        this.progress = progress;
        postInvalidate();
    }


}
