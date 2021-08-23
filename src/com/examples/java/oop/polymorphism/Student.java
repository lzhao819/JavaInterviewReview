package com.examples.java.oop.polymorphism;

public class Student extends Person{
    @Override
    public void run() {
        System.out.println("studentRun");
    }
    public void eat(){
        System.out.println("studentEat");
    }

}
