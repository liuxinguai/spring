package com.github.springframework.ioc.annotation.imp;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author liuxg
 */
public class ImportBeanDefinitionRegistrarImpl implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println(importingClassMetadata.getAnnotationTypes());
        if (!importingClassMetadata.hasAnnotation("man")) {
            registry.registerBeanDefinition("man",new AnnotatedGenericBeanDefinition(Man.class));
        }
    }


}
