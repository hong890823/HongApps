// IMyAidlInterface.aidl
package com.hong.hongipcserver;

// Declare any non-default types here with import statements
import com.hong.hongipcserver.Person;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(int num1,int num2);

    List<Person> addPerson(in Person person);
}
