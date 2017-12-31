package com.hong.hongjavaproxy.proxys;

import com.hong.hongjavaproxy.objects.Car;
import com.hong.hongjavaproxy.objects.House;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * 创建动态代理对象，我用了动态代理就不用再去创建一个和PersonProxy相同功能的PeopleProxy了
 * 这个动态代理就可以做到PersonProxy和PeopleProxy的功能
 * 动态代理不需要实现接口,但是需要指定接口类型
 */
public class ProxyFactory{

    //维护一个目标对象
    private Object target;
    public ProxyFactory(Object target){
        this.target=target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),new ProxyHandler_());
    }

    private class ProxyHandler_ implements InvocationHandler{
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

}
