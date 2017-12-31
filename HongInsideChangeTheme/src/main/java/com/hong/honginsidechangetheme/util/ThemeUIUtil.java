package com.hong.honginsidechangetheme.util;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

import com.hong.honginsidechangetheme.view.ThemeUIInterface;

/**
 * Created by Hong on 2016/7/29.
 */
public class ThemeUIUtil {

    /**
     * 从RootView向下递归改变主题
     * */
    public static void changeTheme(View rootView, Resources.Theme theme){
        if(rootView instanceof ThemeUIInterface){
            ((ThemeUIInterface)rootView).setTheme(theme);
            if(rootView instanceof ViewGroup){
                int count = ((ViewGroup) rootView).getChildCount();
                for(int i=0;i<count;i++){
                    View view = ((ViewGroup) rootView).getChildAt(i);
                    changeTheme(view,theme);
                }
            }

        }
    }

}
