package com.github.springframework.ioc.xml;

import com.github.springframework.ioc.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlComponentScanMain {

    public static void main(String[] args) {
        /**
         * 设置环境变量：env=dev
         */
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-scan.xml");
        System.out.println(applicationContext.getBean(Student.class));
        System.out.println(applicationContext.getBean(Teacher.class));
    }
}
