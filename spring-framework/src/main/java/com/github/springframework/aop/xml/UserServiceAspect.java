package com.github.springframework.aop.xml;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author liuxg
 */
public class UserServiceAspect implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行"+method.getName()+"方法之前");
    }

    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行"+method.getName()+"方法之后");
    }

    public void afterThrowing(Exception exception) {
        System.out.println("执行异常");
        exception.printStackTrace();
    }

}
