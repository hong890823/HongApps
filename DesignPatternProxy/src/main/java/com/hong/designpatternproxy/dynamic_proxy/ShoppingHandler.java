package com.hong.designpatternproxy.dynamic_proxy;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Hong on 2016/9/19.
 * 动态代理用到了反射，针对不同的方法只需要在
 * if里面进行判断就好了
 */
public class ShoppingHandler implements InvocationHandler{

    private Object object;

    public ShoppingHandler(Object object){
        this.object = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if("doShopping".equals(method.getName())){
            Context context = (Context)objects[0];
            long money = (Long) objects[1];
            Toast.makeText(context, "代理黑掉了"+money*0.1+"元", Toast.LENGTH_SHORT).show();
            money = (long)(money-money*0.1);
            String goods = (String)method.invoke(object,context,money);
            goods = "篮球鞋，卫衣，太阳帽";
            Toast.makeText((Context)objects[0], goods, Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}
