package com.examples.java.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//使用FutureTask获取返回值
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<String>(new MyCallable());
        new Thread(task).start();
        if(!task.isDone()){
            System.out.println("Task has not finished, please wait!");
        }
        System.out.println("Task return: "+ task.get());
    }
}
