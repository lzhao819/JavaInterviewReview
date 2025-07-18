package com.examples.java.thread;

public class ThreadInterrupt extends Thread {
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
        ThreadInterrupt t1 = new ThreadInterrupt();
        t1.start();
        Thread.sleep(6000);
        t1.flag = true; // 6秒后子线程自动中断
    }
}
