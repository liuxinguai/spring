package com.github.springframework.aop.xml.aspectj;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectjAopXmlApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop/aspectj.xml");
        PermissionService permissionService = applicationContext.getBean(PermissionService.class);
        permissionService.permisser();
    }
}
