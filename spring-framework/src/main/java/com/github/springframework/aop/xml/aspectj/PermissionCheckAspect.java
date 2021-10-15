package com.github.springframework.aop.xml.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * @author liuxg
 */
public class PermissionCheckAspect {

    public Object arround(ProceedingJoinPoint joinPoint) {
        try {
            System.out.println("权限校验");
            Object proceed = joinPoint.proceed(joinPoint.getArgs());
            System.out.println("权限校验结果："+proceed);
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
