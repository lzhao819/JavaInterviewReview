package com.examples.java.oop.polymorphism;

public class Application {
    public static void main(String[] args) {
        //一个对象的实际类型是确定的
        //new Student();
        //new Person();

        //可以指向的引用类型不确定：父类的引用指向子类

        //Student能调用的方法都是子类自己的或者继承父类的
        Student s1 = new Student();
        //Person 父类型，可以指向子类但是不能调用子类独有的方法
        Person s2 = new Student();
        Object s3 = new Student();

        s1.run();
        s2.run();//子类重写了父类的方法执行子类
        s1.eat();
        //s2.eat();//报错，对象能执行哪些方法主要看对象左边的类型



    }
}
