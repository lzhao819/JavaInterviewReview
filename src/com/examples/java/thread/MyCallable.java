package com.examples.java.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//创建线程方法3: 实现callable接口，适用于需要返回值的情况
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception{
        String value = "Task";
        System.out.println("Read to work");
        Thread.currentThread().sleep(5000);
        System.out.println("Task Done");
        return value;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<String>(myCallable);
        Thread thread1 = new Thread(futureTask);
        thread1.start();
        String result = futureTask.get();
        System.out.println(result);
    }
}
