package com.hong.hongjavaproxy.proxys;

import com.hong.hongjavaproxy.interfaces.IPerson;
import com.hong.hongjavaproxy.objects.Car;
import com.hong.hongjavaproxy.objects.House;
import com.hong.hongjavaproxy.objects.Person;

/**
 * Created by Hong on 2017/9/6.
 * 静态代理类要和目标对象实现同一个接口才叫代理模式
 */

public class PersonProxy implements IPerson{
    private Person person;

    public PersonProxy(Person person) {
        this.person = person;
    }

    @Override
    public String driveCar(Car car) {
        car.setType("货车");//黑代理做手脚了
        return person.driveCar(car);
    }

    @Override
    public String liveInHouse(House house) {
        house.setType("平房");
        return person.liveInHouse(house);
    }
}
