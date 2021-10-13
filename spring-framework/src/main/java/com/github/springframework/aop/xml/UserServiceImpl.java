package com.github.springframework.aop.xml;

public class UserServiceImpl implements UserService {

    @Override
    public void find() {
        System.out.println("查找学生");
    }

}
