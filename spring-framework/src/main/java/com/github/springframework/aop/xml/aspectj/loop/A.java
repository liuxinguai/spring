package com.github.springframework.aop.xml.aspectj.loop;

import lombok.Data;

@Data
public class A {

    private B b;

    @Log
    public void printB() {
        System.out.println(b);
    }


    @Override
    public String toString() {
        return "A";
    }


}
