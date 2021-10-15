package com.github.springframework.aop.xml.aspectj;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectAroundAopXmlApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop/aspect_arround.xml");
        PermissionService permissionService = applicationContext.getBean(PermissionService.class);
        permissionService.role("liuxg");
    }
}
