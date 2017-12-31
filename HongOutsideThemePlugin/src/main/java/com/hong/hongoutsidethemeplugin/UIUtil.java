package com.hong.hongoutsidethemeplugin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

public class UIUtil {
	
	public static String getTextString(Context ctx){
		return ctx.getResources().getString(R.string.app_name);
	}
	
	public static Drawable getImageDrawable(Context ctx){
		return ctx.getResources().getDrawable(R.mipmap.ic_launcher);
	}

	public static View getLayout(Context ctx){
		return LayoutInflater.from(ctx).inflate(R.layout.activity_main, null);
	}
	
	public static int getTextStringId(){
		return R.string.app_name;
	}
	
	public static int getImageDrawableId(){
		return R.mipmap.ic_launcher;
	}
	
	public static int getLayoutId(){
		return R.layout.activity_main;
	}


	//以上方法为之前Demo的方法，用不上，可参考
	public static int getTextColor(Context ctx){
		if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
			return ctx.getResources().getColor(R.color.text_color,ctx.getTheme());
		}
		return ctx.getResources().getColor(R.color.text_color);
	}

	public static float getTextSize(Context ctx){
		return ctx.getResources().getDimension(R.dimen.text_size);
	}

	public static Drawable getBgDrawable(Context ctx){
		return ctx.getResources().getDrawable(R.mipmap.main_bg);
	}
}
