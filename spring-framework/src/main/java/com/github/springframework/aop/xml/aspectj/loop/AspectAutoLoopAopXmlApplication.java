package com.github.springframework.aop.xml.aspectj.loop;

import com.github.springframework.aop.xml.aspectj.NameServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectAutoLoopAopXmlApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop/aspect_auto_loop.xml");
        A a = applicationContext.getBean(A.class);
        B b = applicationContext.getBean(B.class);
        System.out.println(a+","+b);
        a.printB();
        b.printA();
    }
}
