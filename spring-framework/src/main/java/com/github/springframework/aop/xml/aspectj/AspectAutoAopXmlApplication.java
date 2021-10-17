package com.github.springframework.aop.xml.aspectj;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectAutoAopXmlApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop/aspect_auto.xml");
        NameServiceImpl nameService = applicationContext.getBean(NameServiceImpl.class);
        nameService.find("xxxx");
        nameService.service();
    }
}
