package com.hong.hongjavaproxy.objects;

import com.hong.hongjavaproxy.interfaces.IPeople;

/**
 * Created by Hong on 2017/9/6.
 */

public class People implements IPeople{
    private int 年龄;//年龄
    private int 性别;//性别
    private String 名字;//名字

    public People(int 年龄, int 性别, String 名字) {
        this.年龄 = 年龄;
        this.性别 = 性别;
        this.名字 = 名字;
    }

    @Override
    public String toString() {
        return "People{" +
                "年龄=" + 年龄 +
                ", 性别=" + 性别 +
                ", 名字='" + 名字 + '\'' +
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
