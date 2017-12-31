package com.hong.hongbarrage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Hong on 2016/6/20.
 */
public class BarrageView extends FrameLayout {
    private static final String TAG = "BarrageView";
    private String[] data = new String[]{"正义，不过是胜利的又一个别称", "超出三界之外，不在五行之中", "最犀利的剑只为最强大的手所挥动",
        "除暴安良是责任，行善积德是兴趣", "逃避不能解决战争，只会解决你自己", "就算失败也要摆出豪迈的姿态", "血红的月光映照着我的生命以及你的死期",
        "若轻云之蔽日，若流风之回雪","周日被我射熄火了，所以今天是周一","我就是法律的化身，为无辜者代言","要么毁灭你，要么毁灭自己",
        "太无敌而找不到对手也是种无敌的忧伤","保持敬老的美德，能让你们避免被碾压","俗说说得好，有钱男子汉，无钱汉子难","健实的身材来自持久不懈的锻炼",
        "心怀不惧，才能翱翔于天际","凛冬已至，故乡的梅花开了吗","借你们的肉体，见识新发明的威力","来一发吗，满足你","不刷新世界观怎么可能成长"
    };
    private Random random = new Random();
    private boolean isBarrage;
    private Context context;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(isBarrage){
                generateBarrageItem(data[random.nextInt(data.length)]);
                handler.sendEmptyMessageDelayed(0,(int)(Math.random()*500));
            }

        }
    };

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void startBarrage(){
        isBarrage=true;
        handler.sendEmptyMessageDelayed(0,1000);
    }

    public void stopBarrage(){
        isBarrage=false;
    }

    public void generateBarrageItem(String content) {
        TextView item = new TextView(context);
        item.setText(content);
        item.setTextColor(Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
        item.setTextSize((int)((Math.random()+1)*15));
        showBarrageItem(item);
    }

    public void showBarrageItem(final TextView item){
        //ViewGroup addView的时候参数layoutparams是子控件占用ViewGroup的一种布局参数。也就是说wrap_content是作用在BarrageItem上的。
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.topMargin =  getVerticalPos(item);
        addView(item,params);
        ObjectAnimator animator =  ObjectAnimator.ofFloat(item,"translationX",getMeasuredWidth(),-getTextRect(item).width());
        animator.setDuration((int)((Math.random()+1)*3000));
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(item);
            }
        });

        item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isBarrage() {
        return isBarrage;
    }

    private int lastItemHeight;
    private int lastVerticalPos;
    public int getVerticalPos(TextView item){//简单的判断让相邻的两个字幕别互相遮盖，效果不太明显，没有也行。如果想好好判断的话估计需要个集合喽
        int itemHeight = getTextRect(item).height();
        int verticalPos = random.nextInt(getMeasuredHeight()-itemHeight*2);
        if(lastVerticalPos<verticalPos && verticalPos<lastVerticalPos+lastItemHeight)verticalPos+=lastItemHeight;
        if(lastVerticalPos>verticalPos && verticalPos>lastVerticalPos-lastItemHeight)verticalPos-=lastItemHeight;
        lastItemHeight = itemHeight;
        lastVerticalPos = verticalPos;
        return verticalPos;
    }

    /**
     * 获取每个TextView的Rect参数
     * */
    public Rect getTextRect(TextView item){
        Paint paint = item.getPaint();
        String content = item.getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(content,0,content.length(),bounds);
        return bounds;
    }
}
