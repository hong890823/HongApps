package com.hong.designpatternproxy.static_proxy;

import android.content.Context;
import android.widget.Toast;

import com.hong.designpatternproxy.Shopping;


/**
 * Created by Hong on 2016/9/19.
 * 静态代理不是非得实现Shopping接口，你只要能让被代理的对象去实现他要实现的方法就可以了。
 * 比如完全可以用proxyShop方法代替重写的doShop方法，只不过这样的话并不直观方便
 */
public class StaticProxy implements Shopping {
    private Shopping shopping;

    public StaticProxy(Shopping shopping){
        this.shopping = shopping;
    }

    @Override
    public String doShopping(Context context, long money) {
        Toast.makeText(context, "代理黑掉了"+money*0.1+"元", Toast.LENGTH_SHORT).show();
        //代理模式可以任意修改实际对象（shopper）方法（doShop）的参数值
        String goods = shopping.doShopping(context,(long)(money-money*0.1));
        //代理模式可以任意更改实际对象方法的返回值
        goods = "篮球鞋，卫衣，太阳帽";
        return goods;
    }


    public String proxyShop(Context context,long money){
        Toast.makeText(context, "代理黑掉了"+money*0.1+"元", Toast.LENGTH_SHORT).show();
        String goods = shopping.doShopping(context,(long)(money-money*0.1));
        goods = "篮球鞋，卫衣，太阳帽";
        return goods;
    }

}
