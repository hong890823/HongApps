package com.hong.designpatternproxy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hong.designpatternproxy.dynamic_proxy.ShoppingHandler;
import com.hong.designpatternproxy.static_proxy.StaticProxy;

import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 原始购物方法
     * */
    public void originalShopping(View view){
        Shopping li = new Shopper("li");
        Toast.makeText(MainActivity.this, "购买了"+ li.doShopping(this,100), Toast.LENGTH_SHORT).show();
    }

    /**
     * 静态代理购物方法
     * */
    public void staticProxyShopping(View view){
        Shopping li = new Shopper("li");
        Toast.makeText(MainActivity.this, "购买了"+ new StaticProxy(li).doShopping(this,100), Toast.LENGTH_SHORT).show();
    }

    /**
     * 动态代理购物方法
     * */
    public void dynamicProxyShopping(View view){
        Shopping li = new Shopper("li");
        li = (Shopping) Proxy.newProxyInstance(Shopping.class.getClassLoader(),li.getClass().getInterfaces(),new ShoppingHandler(li));
        li.doShopping(this,100);

    }


}
