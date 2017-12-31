package com.hong.hongjavaproxy.proxys;

import com.hong.hongjavaproxy.objects.Car;
import com.hong.hongjavaproxy.objects.House;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by Hong on 2017/9/6.
 */

public class ProxyHandler implements InvocationHandler{

    private Object target;

    public  ProxyHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Type[] types =  method.getParameterTypes();
        if(method.getName().matches("drive.+") && types.length==1 && types[0]==Car.class){
            Car car = (Car) objects[0];
            car.setType("货车");
        }
        if(method.getName().matches("liveIn.+") && types.length==1 && types[0]==House.class){
            House car = (House) objects[0];
            car.setType("平房");
        }
        return method.invoke(target,objects);
    }

}
