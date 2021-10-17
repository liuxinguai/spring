package com.github.springframework.aop.xml.aspectj;

/**
 * @author liuxg
 */
public class NameServiceImpl {

    public String find(String alias) {
        System.out.println("通过别名查找命名服务");
        return "liuxg";
    }

    public void service() {
        System.out.println("提供服务。。。。");
    }

}
