package com.examples.java.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CreatThread {
    static class MyThread extends Thread{
        @Override
        public void run(){
            System.out.println("Start MyThread");
        }
    }
    static class MyRun implements Runnable{
        @Override
        public void run(){
            System.out.println("Start MyRun");
        }
    }
    static class MyCall implements Callable<String> {
        @Override
        public String call(){
            System.out.println("Start MyCall");
            return "success";
        }
    }
    //启动线程
    public static void main(String[] args){
        new MyThread().start();

        new Thread(new MyRun()).start();

        new Thread(()->{
            System.out.println("Lambda");
        }).start();

        Thread t = new Thread(new FutureTask<String>(new MyCall()));
        t.start();

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()->{
            System.out.println("ThreadPool");
        });
        service.shutdown();
    }
}
