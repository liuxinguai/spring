package com.github.springframework.aop.annotation;

public class MyService {

    @Log
    public void service() {
        System.out.println("service");
    }

}
