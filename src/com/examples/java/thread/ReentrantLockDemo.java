package com.examples.java.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable{
    private static ReentrantLock lock = new ReentrantLock(true);//改为true/false来测试
    @Override
    public void run(){
        while (true){
            try{
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " get lock");
                Thread.sleep(1000);//1s
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                lock.unlock();//释放
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo rtld = new ReentrantLockDemo();
        Thread thread1 = new Thread(rtld);
        Thread thread2 = new Thread(rtld);//传入同一个锁
        thread1.start();
        thread2.start();
    }
}
