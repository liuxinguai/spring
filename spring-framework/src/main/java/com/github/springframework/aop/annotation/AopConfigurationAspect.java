package com.github.springframework.aop.annotation;

import org.springframework.context.annotation.*;

@Import(MyService.class)
@Configuration
@EnableAspectJAutoProxy
public class AopConfigurationAspect {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfigurationAspect.class);
        MyService service = context.getBean(MyService.class);
        service.service();
    }


    @Bean("logAspect")
    public LogAspect logAspect() {
        return new LogAspect();
    }

}
