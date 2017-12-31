package com.hong.hongjavareflection;

/**
 * Created by Hong on 2016/9/19.
 * java反射的测试类
 */
public class Actress {
    //女优名字
    private String name;
    //女优作品
    private String oeuvre;

    private static String staticAct(String partner,int count){
        return "苍井空常年和"+partner+"合作出演"+count+"部电影";
    }

    private String act(){
        return name+"出演了"+oeuvre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOeuvre() {
        return oeuvre;
    }

    public void setOeuvre(String oeuvre) {
        this.oeuvre = oeuvre;
    }

}
