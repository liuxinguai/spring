package com.github.springframework.ioc.annotation;


import com.github.springframework.ioc.annotation.imp.ImportConfiguration;
import com.github.springframework.ioc.annotation.imp.Women;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ImportConfiguration.class);
        context.refresh();
        System.out.println(context.getBean(Women.class));
    }
}
