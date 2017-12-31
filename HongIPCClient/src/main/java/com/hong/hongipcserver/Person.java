package com.hong.hongipcserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hong on 2016/6/13.
 * 客户端的实体类包名都得和服务端的一致才可以
 */
public class Person implements Parcelable{
    private String name;
    private int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public Person(Parcel source) {
        this.name = source.readString();
        this.age = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>(){
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
