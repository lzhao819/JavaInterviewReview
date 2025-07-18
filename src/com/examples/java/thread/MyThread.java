package com.examples.java.thread;

//创建线程方法1: 继承Thread
public class MyThread extends Thread{
    private String name;
    public MyThread(String name){
        this.name = name;
    }
    @Override
    public void run(){
        for(int i=0;i<10;i++){
            System.out.println("Thread start: " + this.name +", i" + i);
        }
    }

    public static void main(String[] args){
        MyThread mt1 = new MyThread("Thread1");
        MyThread mt2 = new MyThread("Thread2");
        mt1.start();
        mt2.start();
    }
}
