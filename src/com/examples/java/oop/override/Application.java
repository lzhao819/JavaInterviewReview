package com.examples.java.oop.override;

public class Application {
    public static void main(String[] args) {
        //静态方法和非静态方法区别很大
            //静态方法：方法的调用只和左边（定义的数据类型）有关
            //非静态方法：重写
        A a = new A();
        //父类的引用指向子类
        B b = new A();
        a.staticTest();
        b.staticTest();
        a.nonStaticTest();
        b.nonStaticTest();
    }
}
