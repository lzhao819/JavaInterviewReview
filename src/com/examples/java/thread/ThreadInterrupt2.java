package com.examples.java.thread;

public class ThreadInterrupt2 extends Thread {
    volatile boolean flag = false;//线程执行的退出标记
    @Override
    public void run() {
        while (!flag) {
            try {
                System.out.println("My thread run...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        //1. 打断阻塞线程
//        Thread t1 = new Thread(()-> {
//            System.out.println("T1 start");
//            try {
//                Thread.sleep(5000);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        });
//        t1.start();
//        Thread.sleep(500);
//        t1.interrupt();
//        System.out.println("T1 interrupted state: " + t1.isInterrupted());

        //2. 打断正常线程
        Thread t2 = new Thread(()-> {
            while(true){
                Thread currentThread = Thread.currentThread();
                if(currentThread.isInterrupted()){
                    System.out.println("T1 interrupted state: true");
                    break;
                }
            }
        });
        t2.start();
        Thread.sleep(500);
        t2.interrupt();
    }
}
