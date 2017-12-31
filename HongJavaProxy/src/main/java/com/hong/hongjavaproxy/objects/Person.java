package com.hong.hongjavaproxy.objects;

import com.hong.hongjavaproxy.interfaces.IPerson;

/**
 * Created by Hong on 2017/9/6.
 */

public class Person implements IPerson{
    private int age;//年龄
    private int sex;//性别
    private String name;//名字

    public Person(int age, int sex, String name) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String driveCar(Car car) {
        return car.run();
    }

    @Override
    public String liveInHouse(House house) {
        return house.beLived();
    }
}
