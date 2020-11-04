package com.examples.java.thread;

public class ThreadStartAndRun {
    private static void attack(){
        System.out.println("Fight");
        System.out.println("Current Thread is: " + Thread.currentThread().getName());
    }
    public static void main(String[] args){
        Thread t = new Thread(){
            public void run(){
                attack();
            }
        };
        System.out.println("Current Thread is: " + Thread.currentThread().getName());
        //对比run 和 start的区别
        t.run();
        t.start();//调用了底层的start昂的
    }
}
