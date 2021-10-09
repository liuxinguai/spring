package com.github.springframework.ioc.annotation.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfiguration {

    @Conditional(MyCondition.class)
    @Bean
    public MyOperation myOperation() {
        return new MyOperation();
    }

}
