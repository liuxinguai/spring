package com.github.springframework.ioc.annotation;

import com.github.springframework.ioc.annotation.scan.Car;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScanMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.scan("com.github.springframework.ioc.annotation.componentscan");
        context.refresh();

        System.out.println(context.getBean(Car.class));

    }
}
