package com.github.springframework.aop.xml.aspectj;

public class PermissionService {

    public void permisser() {
        System.out.println("业务方法");
    }

    public String role(String username) {
        System.out.println(username);
        return "admin";
    }

}
