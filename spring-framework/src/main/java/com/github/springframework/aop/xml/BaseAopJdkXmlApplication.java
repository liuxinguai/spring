package com.github.springframework.aop.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseAopJdkXmlApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop/base_jdk.xml");
        UserService userProxy = applicationContext.getBean("userProxy", UserService.class);
        userProxy.find();
    }
}
