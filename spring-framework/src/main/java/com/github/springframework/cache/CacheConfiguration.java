package com.github.springframework.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;


@Import(MyService.class)
@EnableCaching
@Configuration
public class CacheConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CacheConfiguration.class);
        MyService myService = context.getBean(MyService.class);
        System.out.println(myService);
        System.out.println(myService.getStudent());
        System.out.println(myService.getStudent());
        context.getBean(StudentService.class).println();
    }


    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<Cache> caches = new ArrayList<>();
        caches.add(new ConcurrentMapCache("myservice"));
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Bean
    public CacheResolver cacheResolver(CacheManager cacheManager) {
        SimpleCacheResolver cacheResolver = new SimpleCacheResolver(cacheManager);
        return cacheResolver;
    }

    @Bean
    public StudentService studentService() {
        return new StudentService();
    }



}
