package com.github.springframework.aop.xml.aspectj.loop;

import lombok.Data;

@Data
public class B {

    private A a;

    @Log
    public void printA() {
        System.out.println(a);
    }

    @Override
    public String toString() {
        return "B";
    }



}
