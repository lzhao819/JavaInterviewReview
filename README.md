# Java面试知识点

## 第八章 Java多线程与并发

进程和线程的区别

![image-20201104003405591](/Users/lu/Library/Application Support/typora-user-images/image-20201104003405591.png)

![image-20201104003600723](/Users/lu/Library/Application Support/typora-user-images/image-20201104003600723.png)

进程和线程的由来：

串行 ：初期的计算机只能串行执行任务并且需要

批处理：

进程：进程独占内存空间，

线程：

进程和线程的区别：

![image-20201104003710545](/Users/lu/Library/Application Support/typora-user-images/image-20201104003710545.png)

![image-20201104003804819](/Users/lu/Library/Application Support/typora-user-images/image-20201104003804819.png)

Java进程和线程的关系

![image-20201104003846312](/Users/lu/Library/Application Support/typora-user-images/image-20201104003846312.png)

主线程可以创建子线程，原则上要后运行主线程

一个程序是一个可执行的文件，一个进程是一个执行的实例

JVM在创建的时候回同时创建很多其他线程，是多线程的



Thread中start和run的区别：

调用run会沿用主线程执行方法，只是一个普通的方法调用

调用start会创建新的子线程来执行方法，调用了JVM底层的startThread方法（过程如下图）

![image-20201104005339624](/Users/lu/Library/Application Support/typora-user-images/image-20201104005339624.png)



Thread 和 Runnable有什么区别

通过Thread类传入runnable，实现多线程

![image-20201104010730283](/Users/lu/Library/Application Support/typora-user-images/image-20201104010730283.png)

![image-20201104010757501](/Users/lu/Library/Application Support/typora-user-images/image-20201104010757501.png)