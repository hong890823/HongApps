package com.hong.hongjavaproxy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hong.hongjavaproxy.interfaces.IPeople;
import com.hong.hongjavaproxy.interfaces.IPerson;
import com.hong.hongjavaproxy.objects.Car;
import com.hong.hongjavaproxy.objects.House;
import com.hong.hongjavaproxy.objects.People;
import com.hong.hongjavaproxy.objects.Person;
import com.hong.hongjavaproxy.proxys.PersonProxy;
import com.hong.hongjavaproxy.proxys.ProxyFactory;
import com.hong.hongjavaproxy.proxys.ProxyHandler;

import java.lang.reflect.Proxy;

/**
 * 代理模式的出现主要是在不更改目标对象方法的基础上，通过代理类对目标对象的方法进行更改或扩展
 * 下面的方法主要对静态代理和动态代理进行了分析。
 * 不管是静态代理还是动态代理，都要求目标对象实现一个接口。
 * 但是如果目标对象没有实现任何接口的话就需要用到Cglib进行代理了，不过这个先不做研究，这属于纯Java工程师的活了..
 * */
public class MainActivity extends AppCompatActivity {
    private Car car;
    private House house;
    private Person person;
    private People people;
    private TextView resultTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData(){
        car = new Car(10,"跑车","红色");
        house = new House(100,"别墅","白色");
        person = new Person(28,0,"张三");
        people = new People(28,1,"李四");

    }

    private void initView(){
        resultTv = (TextView) findViewById(R.id.result_tv);
    }

    private void specialOwner(Person person){
        car.clearOwner();
        car.setPerson(person);
        house.clearOwner();
        house.setPerson(person);
    }

    private void specialOwner(People people){
        car.clearOwner();
        car.setPeople(people);
        house.clearOwner();
        house.setPeople(people);
    }

    /**
     * 没有代理
     * */
    public void noProxy(View view){
        specialOwner(person);
        String driveResult = person.driveCar(car);
        String liveResult = person.liveInHouse(house);
        resultTv.setText(driveResult+liveResult);
    }

    /**
     * 静态代理
     * */
    public void blackProxy(View view){
        specialOwner(person);
        PersonProxy personProxy = new PersonProxy(person);
        String driveResult = personProxy.driveCar(car);
        String liveResult = personProxy.liveInHouse(house);
        resultTv.setText(driveResult+liveResult);
    }

    /**
     * 动态代理（这个方法传Person和People都可以，稍微改变即可）
     * */
    public void blackJdkProxy(View view){
        specialOwner(person);
        ProxyFactory proxyFactory = new ProxyFactory(person);
        IPerson personProxy = (IPerson) proxyFactory.getProxyInstance();
        String driveResult = personProxy.driveCar(car);
        String liveResult = personProxy.liveInHouse(house);
        resultTv.setText(driveResult+liveResult);
    }

    /**
     * 动态代理的另一种形式（这个方法传Person和People都可以，稍微改变即可）
     * */
    public void blackJdkProxy_(View view){
        specialOwner(people);
        Class<People> targetClass = People.class;
        IPeople peopleProxy = (IPeople) Proxy.newProxyInstance(targetClass.getClassLoader(),targetClass.getInterfaces(),new ProxyHandler(people));
        String driveResult = peopleProxy.driveCar(car);
        String liveResult = peopleProxy.liveInHouse(house);
        resultTv.setText(driveResult+liveResult);
    }


}
