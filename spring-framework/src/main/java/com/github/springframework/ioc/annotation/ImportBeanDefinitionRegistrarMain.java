package com.github.springframework.ioc.annotation;


import com.github.springframework.ioc.annotation.imp.ImportBeanDefinitionRegistrarConfiguration;
import com.github.springframework.ioc.annotation.imp.Man;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author liuxg
 */
public class ImportBeanDefinitionRegistrarMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ImportBeanDefinitionRegistrarConfiguration.class);
        context.refresh();
        System.out.println(context.getBean(Man.class));
    }
}
