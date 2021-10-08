package com.github.springframework.ioc.annotation;

import com.github.springframework.ioc.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationConfig {

    @Bean
    public Student student() {
        Student student = new Student();
        return student;
    }

}
