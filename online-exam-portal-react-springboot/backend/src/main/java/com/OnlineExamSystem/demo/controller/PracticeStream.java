package com.OnlineExamSystem.demo.controller;

@FunctionalInterface
interface    Lambda{
   public abstract void example() ;
}


public class PracticeStream {
    public static void main(String[] args) {
        Lambda lambda=()->System.out.print("Anomyous Inner Class");
        lambda.example();
    }
}

