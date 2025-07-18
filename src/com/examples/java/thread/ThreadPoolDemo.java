package com.examples.java.thread;

import java.util.concurrent.*;

//创建线程方法4: 使用线程池创建
public class ThreadPoolDemo {
    public static void main(String[] args){
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        Future<String> future = newFixedThreadPool.submit(new MyCallable());
        if(!future.isDone()){
            System.out.println("Task has not finished, please wait!");
        }
        try{
            System.out.println(future.get());
        }catch (InterruptedException | ExecutionException e){
            System.out.println(e.getMessage());
        } finally {
            newFixedThreadPool.shutdown();
        }
    }
}
