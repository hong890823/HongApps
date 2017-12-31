package com.hong.designpatternproxy;

import android.content.Context;

/**
 * Created by Hong on 2016/9/19.
 * 购物的接口，里面含有购物的方法
 */
public interface Shopping {
    /**
     * 购物的方法
     * */
    String doShopping(Context context,long money);
}
