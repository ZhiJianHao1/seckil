package com.zhi.seckill.infrastructure.cache.local.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhiJH
 * @description 本地缓存工厂
 * @date 2024/5/22
 */
public class LocalGuavaFactory {

    public static <K, V> Cache<K, V> getLocalCache() {
        return CacheBuilder.newBuilder().initialCapacity(15).concurrencyLevel(5).expireAfterWrite(5, TimeUnit.SECONDS).build();
    }
}
