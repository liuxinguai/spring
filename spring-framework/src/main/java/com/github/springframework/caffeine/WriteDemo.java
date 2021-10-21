package com.github.springframework.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.function.Function;

public class WriteDemo {
    public static void main(String[] args) {
        Cache<Object, Object> cache = Caffeine.newBuilder().build();
        cache.put("liuxg","234");
        System.out.println(cache.getIfPresent("liuxg"));
    }
}
