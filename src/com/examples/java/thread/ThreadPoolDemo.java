package com.examples.java.thread;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args){
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        Future<String> future = newCachedThreadPool.submit(new MyCallable());
        if(!future.isDone()){
            System.out.println("Task has not finished, please wait!");
        }
        try{
            System.out.println(future.get());
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }finally {
            newCachedThreadPool.shutdown();
        }
    }
}
