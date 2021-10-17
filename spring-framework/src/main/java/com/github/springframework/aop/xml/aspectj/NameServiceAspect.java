package com.github.springframework.aop.xml.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class NameServiceAspect {

    //定义切入点
    @Pointcut("execution(* com.github.springframework.aop.xml.aspectj.NameServiceImpl.*(..))")
    public void pointcut(){}


    @Before("pointcut()")
    public void before() {
        System.out.println("执行之前");
    }

    @Around("pointcut()")
    public Object arround(ProceedingJoinPoint proceedingJoinPoint) {

        try {
            Object proceed = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            System.out.println("执行结果："+proceed);
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        } finally {
            System.out.println("执行清理工作");
        }
    }

}
