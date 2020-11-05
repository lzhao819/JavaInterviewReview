package com.examples.java.thread;

public class WaitSleepDemo {
    public static void main(String[] args){
        final Object lock = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread A is waiting to get lock");
                synchronized (lock){
                    try{
                        System.out.println("Thread A gets lock");
                        Thread.sleep(20);
                        System.out.println("Thread A do wait method");
                        lock.wait();
                        System.out.println("Thread A is done");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //第一个线程A开始后，让主线程sleep 10ms再执行B，保证A比B先开始
        try{
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread B is waiting to get lock");
                synchronized (lock){
                    try{
                        System.out.println("Thread B gets lock");
                        System.out.println("Thread B is sleeping 10ms");
                        Thread.sleep(10);
                        System.out.println("Thread B is done");
                        lock.notify();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
