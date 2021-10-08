package com.github.springframework.ioc.annotation;

import com.github.springframework.ioc.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(ConfigurationConfig.class);
        annotationConfigApplicationContext.refresh();
        /**
         *
         * 1.初始化DefaultListableBeanFactory即：BeanDefinitionRegister和DefaultSingletonBeanRegistry，通过构建：new DefaultListableBeanFactory()
         *  疑问：为啥需要在AnnotationConfigApplicationContext时就立马初始化DefaultListableBeanFactory而不在refresh()方法中进行初始化？
         *      因为在构造函数中需要预先加载一些内部的BeanFactoryPostProcessorDefinition和BeanPostProcessorDefinition，故需要先初始化DefaultListableBeanFactory-->AnnotationConfigUtils.registerAnnotationConfigProcessors
         *      初始化了BeanFactoryPostProcessorDefinition：ConfigurationClassPostProcessor
         *      初始化BeanPostProcessorDefinition：
         *      初始化事件监听支持：
         *
         */
        Student bean = annotationConfigApplicationContext.getBean(Student.class);

        System.out.println(bean);
    }


}
