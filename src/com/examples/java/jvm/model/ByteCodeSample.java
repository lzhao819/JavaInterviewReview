package com.examples.java.jvm.model;

public class ByteCodeSample {
    public static int add(int a, int b){
        int c =0;
        c = a+b;
        return c;
    }}
    // javac com/examples/java/model/ByteCodeSample.java
    // javap -verbose com/examples/java/model/ByteCodeSample.class
