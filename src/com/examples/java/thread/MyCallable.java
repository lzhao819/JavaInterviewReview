package com.examples.java.thread;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception{
        String value = "Task";
        System.out.println("Read to work");
        Thread.currentThread().sleep(5000);
        System.out.println("Task Done");
        return value;
    }
}
