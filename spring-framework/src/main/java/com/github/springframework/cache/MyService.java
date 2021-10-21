package com.github.springframework.cache;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author liuxg
 */
public class MyService {

    @Cacheable(value = "myservice")
    public Student getStudent() {
        System.out.println("查找student");
        Student student = new Student();
        student.setName("liuxg");
        return student;
    }

}
