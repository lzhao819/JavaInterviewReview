package com.examples.java.oop.override;

//继承
public class A extends B{
    public static void staticTest() {
        System.out.println("A==>staticTest");
    }
    @Override
    public void nonStaticTest() {
        System.out.println("A==>nonStaticTest");
    }

}
