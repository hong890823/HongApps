package com.hong.hongoutsidechangetheme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hong.hongoutsidechangetheme.util.ChangeThemeUtil;
import com.hong.hongoutsidechangetheme.vo.Student;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by Hong on 2016/9/12.
 * 这个项目主要是应用一些插件化换肤的方式(各个换肤方式不可连续使用)。
 * 更多的插件化，热更新，增量升级方案
 * 可能再另起Module应用
 */
public class MainActivity extends AppCompatActivity {
    private ViewGroup mainBg;
    private TextView TvOne,TvTwo;

    protected AssetManager mAssetManager;
    protected Resources mResources;
    protected Resources.Theme mTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBg = (LinearLayout)findViewById(R.id.main_bg);
        TvOne = (TextView)findViewById(R.id.text_view_one);
        TvTwo = (TextView)findViewById(R.id.text_view_two);
    }

    /**
     * 插件化换肤方案1（调用已经安装的APK替换控件指定资源ID的值进行换肤）
     * */
    public void changeTheme_1(View view) throws PackageManager.NameNotFoundException {
        String pluginPackageName = "com.hong.hongoutsidethemeplugin";
        if(ChangeThemeUtil.checkPackageInstalled(this,pluginPackageName)){
            Context pluginContext = createPackageContext(pluginPackageName, Context.CONTEXT_IGNORE_SECURITY);//获取皮肤APK的Context
            Resources pluginResources = pluginContext.getResources();//获取皮肤APK的资源类
            //三个参数分别是皮肤APK的属性名，属性类型，包名.(属性类型和资源文件中的节点名称是一致的)
            int bgColor = pluginResources.getIdentifier("bg_color","color",pluginPackageName);
            int textColor = pluginResources.getIdentifier("text_color","color",pluginPackageName);
            int textSize = pluginResources.getIdentifier("text_size","dimen",pluginPackageName);
            Toast.makeText(MainActivity.this, "背景色ID"+bgColor+" 字体色ID"+textColor+" 字体大小ID"+textSize, Toast.LENGTH_SHORT).show();

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                mainBg.setBackgroundColor(pluginResources.getColor(bgColor,pluginContext.getTheme()));
                TvOne.setTextColor(pluginResources.getColor(textColor,pluginContext.getTheme()));
                TvTwo.setTextColor(pluginResources.getColor(textColor,pluginContext.getTheme()));
            }else{
                mainBg.setBackgroundColor(pluginResources.getColor(bgColor));
                TvOne.setTextColor(pluginResources.getColor(textColor));
                TvTwo.setTextColor(pluginResources.getColor(textColor));
                System.out.println("color="+pluginResources.getColor(textColor));
            }

            TvOne.setTextSize(pluginResources.getDimension(textSize));
            TvTwo.setTextSize(pluginResources.getDimension(textSize));
        }else{
            Toast.makeText(MainActivity.this, "请安装指定的皮肤包", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *插件化换肤方案2（调用沙盒内未安装的APK插件换肤）
     * */
    public void changeTheme_2(View view){
        String apkName = "HongOutsideThemePlugin-debug.apk";//这个名字一定要和你push入data/data/包名/cache目录下的apk的名字一致,不同的apk名字就代表了不同的插件皮肤包了
        File fileDex = getDir("dex",0);//解压优化出的dex文件，这个方法会自动生成app_dex文件夹
        String fileDir = getCacheDir().getAbsolutePath();//预先已经把插件apk(HongOutsideThemePlugin.apk)文件放在Cache目录下，所以这里获取该应用缓存目录
        String filePath = fileDir+File.separator+apkName;//获取预先下载或放入的apk文件目录
        /*
        * 参数说明：第一个参数是插件apk的路径；第二个参数是解压出的dex文件的路径；第三个参数是依赖的库文件；第四个参数是父类加载器
        * */
        DexClassLoader loader = new DexClassLoader(filePath,fileDex.getAbsolutePath(),null,getClassLoader());

        try {
            AssetManager am = AssetManager.class.newInstance();
            Method addAssetPath = am.getClass().getMethod("addAssetPath", String.class);//addAssetPath是一个隐藏方法，所以需要用到反射获取
            addAssetPath.invoke(am,filePath);
            System.out.println("invoke返回值"+ addAssetPath.invoke(am,filePath));//如果invoke的返回值是0就是有问题了

            //以下步骤是把apk中的getAssets(),getResources(),getTheme()真正替换成插件中的资源
            mAssetManager = am;//这里要注意看am.invoke中用到了filePath把插件的路径加载进来了
            Resources superRes = super.getResources();
            mResources = new Resources(am, superRes.getDisplayMetrics(),superRes.getConfiguration());
            mTheme = mResources.newTheme();
            mTheme.setTo(super.getTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContent(loader);
    }

    @SuppressLint("NewApi")
    private void setContent(DexClassLoader classLoader){
        try{
//            Class.forName的方法是不可行的。
//            dexPath	需要装载的APK或者Jar文件的路径。包含多个路径用File.pathSeparator间隔开,在Android上默认是 ":"
//            optimizedDirectory	优化后的dex文件存放目录，不能为null
//            libraryPath	目标类中使用的C/C++库的列表,每个目录用File.pathSeparator间隔开; 可以为 null
//            parent	该类装载器的父装载器，一般用当前执行类的装载器

            Class clazz = classLoader.loadClass("com.hong.hongoutsidethemeplugin.UIUtil");
            Method method = clazz.getMethod("getTextColor", Context.class);
            int color = (Integer) method.invoke(null, this);
            TvOne.setTextColor(color);
            TvTwo.setTextColor(color);
            method = clazz.getMethod("getTextSize", Context.class);
            float textSize = (Float)method.invoke(null, this);
            TvOne.setTextSize(textSize);
            TvTwo.setTextSize(textSize);
            method = clazz.getMethod("getBgDrawable", Context.class);
            Drawable drawable = (Drawable)method.invoke(null, this);
            mainBg.setBackground(drawable);
        }catch(Exception e){
            Log.i("Loader", "error:"+Log.getStackTraceString(e));
        }
    }

    /**
     * 该方法只是为了理解一下method的invoke方法
     * */
    public void testMethodInvoke(View view) throws Exception{
        Student sd=new Student();
        sd.setId(1);
        sd.setName("测试invoke");
        sd.setSex("未知");
        sd.setAge("10000");
        sd.setBrithday("19920205");
        sd.setAddress("湖南");
        Object[] args = new Object[]{};
        StringBuilder sb = new StringBuilder();
        sb.append("<Object.XmlString> start");
        for (Method m : sd.getClass().getMethods()) {
            if (m.getName().startsWith("get")) {
                System.out.println(m.getName());
                sb.append("  <" + m.getName().substring(3) + ">");
                sb.append(m.invoke(sd,args));
                System.out.println("---"+m.invoke(sd,args));
            }
        }
        System.out.println(sb.toString());
    }


    @Override
    public AssetManager getAssets() {
        return mAssetManager == null ? super.getAssets() : mAssetManager;
    }

    @Override
    public Resources getResources() {
        return mResources == null ? super.getResources() : mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        return mTheme == null ? super.getTheme() : mTheme;
    }
}
