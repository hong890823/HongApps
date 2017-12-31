package com.hong.designpatternproxy;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Hong on 2016/9/19.
 * 实际购买者
 */
public class Shopper implements Shopping{
    private String name;

    public Shopper(String name){
        this.name = name;
    }
    @Override
    public String doShopping(Context context,long money) {
        //System.out.println(String.format("花费s%",money));
        Toast.makeText(context, name+"花费"+money+"元", Toast.LENGTH_SHORT).show();
        return "篮球鞋,卫衣,运动帽";
    }
}
