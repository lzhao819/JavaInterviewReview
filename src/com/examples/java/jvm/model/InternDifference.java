package com.examples.java.jvm.model;

public class InternDifference {
    public static void main(String[] args) {
        String s = new String("a");//""引号中的对象都会放入常量池，new创建出的对象s放在heap
        s.intern();
        String s2 = "a";//在常量池中找
        System.out.println(s == s2);

        String s3 = new String("a") + new String("a");
        s3.intern();
        String s4 = "aa";
        System.out.println(s3 == s4);
    }
}
//结果： JDK6 输出false false
//      JDK8 输出false true
