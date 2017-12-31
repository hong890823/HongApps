package com.hong.hongjavaproxy.objects;

import com.hong.hongjavaproxy.interfaces.ICar;

/**
 * Created by Hong on 2017/9/6.
 */

public class Car implements ICar {

    private int size;//大小
    private String type;//类型
    private String color;//颜色
    private Person person;//车主1号
    private People people;//车主2号

    public Car(int size, String type, String color) {
        this.size = size;
        this.type = type;
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Car{" +
                "size=" + size +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", person=" + person +
                ", people=" + people +
                '}';
    }

    @Override
    public String run() {
        String result = this.toString()+"-这辆车跑起来了";
        return result;
    }

    @Override
    public void clearOwner(){
        person = null;
        people = null;
    }
}
