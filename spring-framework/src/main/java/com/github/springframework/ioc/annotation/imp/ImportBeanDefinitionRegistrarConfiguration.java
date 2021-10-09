package com.github.springframework.ioc.annotation.imp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(ImportBeanDefinitionRegistrarImpl.class)
@Configuration
public class ImportBeanDefinitionRegistrarConfiguration {
}
