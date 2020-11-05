package com.examples.java.thread;


public class CycleWait implements Runnable{
    private String value;
    public void run(){
        try{
            Thread.currentThread().sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        value = "We have data now";
    }
    public static void main(String[] args) throws InterruptedException{
        CycleWait cw = new CycleWait();
        Thread t = new Thread(cw);
        t.start();
//        //1 主线程等待法
//        while (cw.value==null){
//            Thread.currentThread().sleep(100);
//        }
        //2 使用Thread类join()方法
        t.join();
        System.out.println("value: "+cw.value);
    }
}
