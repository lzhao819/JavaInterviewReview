package com.examples.java.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectSample {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class rc = Class.forName("com.examples.java.reflect.Robot");
        Robot r  = (Robot)rc.newInstance();
        System.out.println("Class name is "+ rc.getName());
        Method getHello = rc.getDeclaredMethod("throwHello", String.class);//可以获取所有方法，但不能获取继承的方法
        getHello.setAccessible(true);//由于是私有的，要set才可以获取到
        Object str = getHello.invoke(r, "Bob");
        System.out.println("getHello result is " + str);
        Method sayHi = rc.getMethod("sayHi", String.class);//可以获取所有public方法，和继承的public方法
        sayHi.invoke(r,"Welcome");
        Field name = rc.getDeclaredField("name");
        name.setAccessible(true);
        name.set(r,"Alice");
        sayHi.invoke(r,"Welcome");
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));

    }
}
