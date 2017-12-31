package com.hong.hongoutsidechangetheme.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by Hong on 2016/9/13.
 */
public class ChangeThemeUtil {

    /**
     * 检测指定皮肤包是否已经安装
     * */
    public static boolean checkPackageInstalled(Context context,String packageName){
        PackageManager pm = context.getPackageManager();
        //获取已经授予权限的已安装包的集合
        List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for(PackageInfo info:packageInfos){
            if(packageName.equals(info.packageName))return true;
        }
        return false;
    }

}
