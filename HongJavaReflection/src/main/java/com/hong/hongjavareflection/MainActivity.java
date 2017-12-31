package com.hong.hongjavareflection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Actress actress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actress = new Actress();
        actress.setName("苍井空");
        actress.setOeuvre("我和玉玺的故事");


    }

    public void getReflectionFiled(View view){
        try {
            Class Actress = Class.forName("com.hong.hongjavareflection.Actress");
            Field[] fields = Actress.getDeclaredFields();//getDeclaredFields返回所有修饰类型的字段，getFields只返回带有public修饰符的字段
            for(Field field:fields){
                field.setAccessible(true);//由于属性是private的，如果不设置允许访问会报错的
                //获取属性的value值
                Object value = field.get(actress);
                if(value!=null)
                Toast.makeText(MainActivity.this,value+" ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getReflectionMethod(View view){
        try {
            Class Actress = Class.forName("com.hong.hongjavareflection.Actress");
            Method act = Actress.getDeclaredMethod("act");
            act.setAccessible(true);
            Toast.makeText(MainActivity.this, act.invoke(actress).toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getReflectionStaticMethod(View view){
        try {
            Class Actress = Class.forName("com.hong.hongjavareflection.Actress");
            Method act = Actress.getDeclaredMethod("staticAct",String.class,int.class);//后两个参数是staticAct方法的形参
            act.setAccessible(true);
            //针对静态方法的invoke方法不需要传入实例参数，后两个是staticAct的实参
            Object[] objects = new Object[]{"玉玺",10};
            Toast.makeText(MainActivity.this, act.invoke(null,objects).toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, act.invoke(null,"玉玺"，10).toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //其实反射蛮简单的，获取方法的时候Class.getDeclareMethod用形参，调用方法的时候Method.invoke用实参
}
