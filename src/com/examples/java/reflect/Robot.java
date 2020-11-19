package com.examples.java.reflect;

public class Robot {
    private String name;
    public void sayHi(String helloSentence){
        System.out.println(helloSentence + " " + name);
    }
    private String throwHello(String tag){
        return "Hello " + tag;
    }
    static {//此静态代码块在初始化的时候进行
        System.out.println("Hello Robot");
    }
}
