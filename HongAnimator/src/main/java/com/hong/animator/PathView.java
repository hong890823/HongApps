package com.hong.animator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hong on 2016/5/31.
 */
public class PathView extends FrameLayout implements View.OnClickListener{
    private int[] ids = new int[]{R.id.a,R.id.b,R.id.c,R.id.d,R.id.e,R.id.f,R.id.g,R.id.h};
    private List<ImageView> icons = new ArrayList<>();

    private Context context;
    private boolean isExpand;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_path,this);
        for(int id:ids){
            ImageView icon = (ImageView)view.findViewById(id);
            icons.add(icon);
            icon.setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.a:
                if(isExpand){
                    contractAnim();
                }else{
                    expandAnim();
                }
                break;
            default:
                Toast.makeText(context,"您点击了"+v.getId(),Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /*
    * 展开动画
    * */
    public void expandAnim(){
        final ImageView a = icons.get(0);
        for(int i=1;i<icons.size();i++){
            ObjectAnimator animator = ObjectAnimator.ofFloat(icons.get(i),"translationX",0,100*i);
            animator.start();
        }
        /**
         * 以上为ObjectAnimator需要getset属性支持的方式，以下为ValueAnimator不需要setget属性支持的方式
         * */
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(360);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                a.setRotation((Float)animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
        isExpand=true;
    }

    /*
    * 关闭动画
    * */
    public void contractAnim(){
        isExpand=false;
    }


}
