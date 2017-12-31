package com.hong.honginsidechangetheme.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hong.honginsidechangetheme.util.ViewAttributeUtil;

/**
 * Created by Hong on 2016/7/28.
 * 继承自那种布局都可以，但是比如如果是LinearLayout，那么加入蒙版View的时候即使LayoutParams是全部，但是也
 * 会加到下面去，到时候动画可能就只有下面那部分显示了
 */
public class HongViewGroup extends FrameLayout implements ThemeUIInterface{

    private int hong_bg_id;

    public HongViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        hong_bg_id = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    /**
     * 在递归改变主题的方法中循环调用
     * */
    @Override
    public void setTheme(Resources.Theme theme) {
        ViewAttributeUtil.applyBackgroundDrawable(this,theme,hong_bg_id);
    }

}
