package com.examples.java.oop.abstructClassDemo;

//abstract 抽象类：extends 单继承
public abstract class Action {
    //约束，有人帮我们实现
    //抽象方法：只有方法名，没有具体实现
    public abstract void doSomething();
    //不能new这个抽象类，只能靠子类去实现。
    //抽象方法必须在抽象类中
    //抽象类中可以有普通方法
}
