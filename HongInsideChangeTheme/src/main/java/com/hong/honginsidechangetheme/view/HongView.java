package com.hong.honginsidechangetheme.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hong.honginsidechangetheme.util.ViewAttributeUtil;

/**
 * Created by Hong on 2016/7/28.
 */
public class HongView extends TextView implements ThemeUIInterface{

    private int text_color_id;
    private int text_size_id;

    public HongView(Context context, AttributeSet attrs) {
        super(context, attrs);
        text_color_id = ViewAttributeUtil.getTextColorAttribute(attrs);
        text_size_id = ViewAttributeUtil.getTextSizeAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme theme) {
        ViewAttributeUtil.applyTextColor(this,theme,text_color_id);
        ViewAttributeUtil.applyTextSize(this,theme,text_size_id);
    }
}
