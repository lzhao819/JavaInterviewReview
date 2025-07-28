package com.examples.java.thread;

//创建线程方法2: 实现Runnable接口
public class MyRunnable implements Runnable{
    private String name;
    public MyRunnable(String name){
        this.name = name;
    }
    @Override
    public void run(){
        for(int i=0;i<10;i++){
            System.out.println("Thread start: " + this.name +", i" + i);
        }
    }
    public static void main(String[] args){
        MyRunnable myRunnable = new MyRunnable("MyRunnable");
        //runnable中没有run方法，所以要创建thread实例来执行
        Thread thread1 = new Thread(myRunnable);
        Thread thread2 = new Thread(myRunnable);
        thread1.start();
        thread2.start();
    }
}
