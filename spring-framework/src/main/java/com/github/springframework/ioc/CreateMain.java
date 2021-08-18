package com.github.springframework.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CreateMain {
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        //1.
        Object newObject = new Object();
        Constructor<Object> constructor = Object.class.getDeclaredConstructor();
        //final Constructor<T> c = getConstructor0(empty, Member.DECLARED);
        //Constructor<T> tmpConstructor = cachedConstructor;
        //return tmpConstructor.newInstance((Object[])null);
        Object newInstance = Object.class.newInstance();
        Object newContructor = constructor.newInstance();
        System.out.println(newInstance);
        System.out.println(newObject);
        System.out.println(newContructor);
    }
}
