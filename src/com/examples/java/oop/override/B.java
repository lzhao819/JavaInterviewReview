package com.examples.java.oop.override;

//重写都是方法的重写，和属性无关
public class B {
    public static void staticTest() {
        System.out.println("B==>staticTest");
    }
    public void nonStaticTest() {
        System.out.println("B==>nonStaticTest");
    }
}
