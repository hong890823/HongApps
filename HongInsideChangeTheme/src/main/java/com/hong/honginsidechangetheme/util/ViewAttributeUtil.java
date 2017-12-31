package com.hong.honginsidechangetheme.util;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hong.honginsidechangetheme.view.ThemeUIInterface;

/**
 * Created by Hong on 2016/7/29.
 */
public class ViewAttributeUtil {
    private static final String TAG = "ViewAttributeUtil";

    /**
     * 获取自定义属性的ID号
     * @return 返回值将作为applyBackgroundDrawable等方法的参数
     * */
    public static int getAttributeValue(AttributeSet attrs,int attrInt){
        int value = -1;
        int count = attrs.getAttributeCount();
        for(int i=0;i<count;i++){
            if(attrs.getAttributeNameResource(i)==attrInt) {//找到指定的系统属性
                String str = attrs.getAttributeValue(i);//获取系统属性的值
                if (str != null && str.startsWith("?")) {//确认值是自定义属性，比如？2130771973
                    value = Integer.valueOf(str.substring(1, str.length())).intValue();//截取自定义属性id值
                }
            }
        }
        return value;
    }

    public static int getBackgroundAttribute(AttributeSet attrs){
        int value = getAttributeValue(attrs,android.R.attr.background);
        return value;
    }

    public static int getTextColorAttribute(AttributeSet attrs){
        int value = getAttributeValue(attrs,android.R.attr.textColor);
        return value;
    }

    public static int getTextSizeAttribute(AttributeSet attrs){
        int value = getAttributeValue(attrs,android.R.attr.textSize);
        return value;
    }

    /**
    * 根据自定义属性的id，在自定义的Theme中找到这些自定义属性id的值，再把值动态的赋值给控件的相应原生属性上
    * */
    public static void applyBackgroundDrawable(ThemeUIInterface ti, Resources.Theme theme,int attrId){
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});//获取指定主题指定属性的值
        Drawable drawable = ta.getDrawable(0);
        if(null != ti){
            ti.getView().setBackgroundDrawable(drawable);//应用到系统属性
        }
        ta.recycle();
    }

    public static void applyTextColor(ThemeUIInterface ti, Resources.Theme theme,int attrId){
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
        int color = ta.getColor(0,0);
        if(null != ti && ti instanceof  TextView){
            ((TextView)(ti.getView())).setTextColor(color);
        }
        ta.recycle();
    }

    public static void applyTextSize(ThemeUIInterface ti, Resources.Theme theme,int attrId){
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
        int size = ta.getDimensionPixelSize(0,0);
        if(null != ti && ti instanceof  TextView){
            ((TextView)(ti.getView())).setTextSize(size);
        }
        ta.recycle();
    }

}
