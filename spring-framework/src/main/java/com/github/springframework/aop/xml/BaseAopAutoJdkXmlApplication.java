package com.github.springframework.aop.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseAopAutoJdkXmlApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop/base_jdk_auto.xml");
        UserService userProxy = applicationContext.getBean("userService",UserService.class);
        userProxy.find();
    }
}
