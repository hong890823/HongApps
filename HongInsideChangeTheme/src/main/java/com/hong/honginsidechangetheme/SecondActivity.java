package com.hong.honginsidechangetheme;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.hong.honginsidechangetheme.util.ThemeUIUtil;
import com.hong.honginsidechangetheme.view.HongView;
import com.hong.honginsidechangetheme.view.HongViewGroup;
/**
 * 由于一般应用切换主题的入口都只有一个界面，所以该Activity中的方式可以保证该页面不需要重启就可以更换主题
 * 其他的页面则无所谓了，原理主要是获取指定主题自定义属性的值，然后递归的赋值到整个页面相应控件的相应属上
 * 应用到了attrs中的自定义属性，style中不同主题对自定义属性的不同实现，activity绑定布局中引用自定义属性
 * */
public class SecondActivity extends Activity{

    private boolean isRed;

    private HongViewGroup hongViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.theme_blue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        hongViewGroup = (HongViewGroup)findViewById(R.id.hong_view_group);
        HongView view = (HongView)findViewById(R.id.hong_view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                secondChangeTheme();
            }
        });
    }

    public void secondChangeTheme(){
        isRed = !isRed;
        if(isRed){
            setTheme(R.style.theme_red);
        }else{
            setTheme(R.style.theme_blue);
        }

        Bitmap drawingBitmap = getDrawingBitmap(hongViewGroup);
        changeThemeByMasking(hongViewGroup,drawingBitmap);
    }

    /**
     * 获取截屏的图像
     * */
    private Bitmap getDrawingBitmap(View rootView){
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);
        Bitmap bitmap = rootView.getDrawingCache();
        Bitmap drawingBitmap = Bitmap.createBitmap(bitmap);
        rootView.setDrawingCacheEnabled(false);//一定要设置false还原缓存机制否则第二次切换不能正常取得过渡效果
        return drawingBitmap;
    }

    /**
     * 用一个蒙版动画来过渡切换主题
     * */
    private void changeThemeByMasking(final ViewGroup rootView, final Bitmap bitmap){
        final View maskingView = new View(this);
        maskingView.setBackgroundDrawable(new BitmapDrawable(getResources(),bitmap));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(maskingView,params);

        maskingView.animate().alpha(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootView.removeView(maskingView);
                bitmap.recycle();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ThemeUIUtil.changeTheme(rootView,getTheme());//真正切换主题的方法
            }
        }).start();
    }

}
