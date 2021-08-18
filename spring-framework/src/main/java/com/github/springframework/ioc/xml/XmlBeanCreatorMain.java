package com.github.springframework.ioc.xml;

import com.github.springframework.ioc.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlBeanCreatorMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        Student student = applicationContext.getBean(Student.class);
        System.out.println(student);
    }
}
