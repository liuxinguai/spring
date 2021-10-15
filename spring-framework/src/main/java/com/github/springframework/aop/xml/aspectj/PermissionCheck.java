package com.github.springframework.aop.xml.aspectj;

public class PermissionCheck {

    public void before() {
        System.out.println("检验权限");
    }

    public void after() {
        System.out.println("完成权限校验清除功能");
    }

}
