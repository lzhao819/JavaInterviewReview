package com.examples.java.oop.staticDemo;

public class Person {
    //2 和类同时产生，在构造方法之前执行，可用于赋初始值
    {
        System.out.println("匿名代码块");
    }

    //1 只执行一次
    static {
        System.out.println("静态代码块");
    }

    //3
    public Person(){
        System.out.println("构造方法");
    }

    public static void main(String[] args) {
        Person person1 = new Person();
        System.out.println("==================");
        Person person2 = new Person();
    }
}
