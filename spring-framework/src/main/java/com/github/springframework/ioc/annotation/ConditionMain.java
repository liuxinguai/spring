package com.github.springframework.ioc.annotation;

import com.github.springframework.ioc.annotation.conditional.ConditionalConfiguration;
import com.github.springframework.ioc.annotation.conditional.MyOperation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author liuxg
 */
public class ConditionMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ConditionalConfiguration.class);
        context.refresh();
        System.out.println(context.getBean(MyOperation.class));
    }
}
