package com.hong.honginsidechangetheme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 这种切换主题的方式比较简单，但是需要重启Activity，不能直接应用到整个Application上
 * 应用到了attrs中的自定义属性，style中不同主题对自定义属性的不同实现，activity绑定布局中引用自定义属性
 * */
public class FirstActivity extends AppCompatActivity {

    private static boolean isRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isRed){
            setTheme(R.style.theme_red);
        }else{
            setTheme(R.style.theme_blue);
        }
        setContentView(R.layout.activity_first);
    }

    public void firstChangeTheme(View view){
        isRed = !isRed;
        Intent intent = new Intent(this,this.getClass());
        //实际测试中用getIntent()切换过程中部分机型会有闪白
        this.finish();
        overridePendingTransition(0, 0);//取消切换动画
        startActivity(intent);
    }

    public void startSecondActivity(View view){
       startActivity(new Intent(this,SecondActivity.class));
    }

}
