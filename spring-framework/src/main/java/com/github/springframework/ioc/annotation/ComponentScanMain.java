package com.github.springframework.ioc.annotation;

import com.github.springframework.ioc.annotation.componentscan.Dog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.github.springframework.ioc.annotation.componentscan")
public class ComponentScanMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ComponentScanMain.class);
        context.refresh();
        System.out.println(context.getBean(Dog.class));
    }
}
