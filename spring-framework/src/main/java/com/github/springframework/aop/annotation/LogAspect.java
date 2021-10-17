package com.github.springframework.aop.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogAspect {


    @Around("@annotation(Log)")
    public Object arround(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            System.out.println(proceedingJoinPoint.getSignature().getName()+"开始执行");
            Object proceed = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            System.out.println(proceedingJoinPoint.getSignature().getName()+"执行结果："+proceed);
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        } finally {
            System.out.println("清理日志工具");
        }

    }

}
