package com.examples.java.reflect;

public class LoadDifference {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader cl = Robot.class.getClassLoader();//获取ClassLoader
        //Class r = Class.forName("com.examples.java.reflect.Robot");
        //Class.forName("com.mysql.jdbc.Driver");//需要运行静态代码块，所以要用forName
        //classLoader 在 spring ioc中有应用，因为其为了加快启动过程采用lazy loading，不需要先初始化
    }
}
