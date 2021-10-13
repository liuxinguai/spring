package com.github.springframework.aop.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseAopXmlApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop/base.xml");
        UserService userProxy = applicationContext.getBean("userProxy", UserService.class);
        userProxy.find();
    }
}
