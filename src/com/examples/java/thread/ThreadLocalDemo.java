package com.examples.java.thread;

public class ThreadLocalDemo {
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static void main(String[] args){
        new Thread(()->{
            String name = Thread.currentThread().getName();
            threadLocal.set("Class1");
            print(name);
            System.out.println(name + "-after remove:" + threadLocal.get());
        }, "T1").start();
        new Thread(()->{
            String name = Thread.currentThread().getName();
            threadLocal.set("Class2");
            print(name);
            System.out.println(name + "-after remove:" + threadLocal.get());
        },  "T2").start();
    }
    private static void print(String str){
        System.out.println(str + ":" + threadLocal.get());
        threadLocal.remove();
    }
}
