package com.github.springframework.aop.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseAopCglibXmlApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop/base_cglib.xml");
        LoginService loginService = applicationContext.getBean("loginProxy", LoginService.class);
        loginService.login("liuxg","liuxg");
    }
}
