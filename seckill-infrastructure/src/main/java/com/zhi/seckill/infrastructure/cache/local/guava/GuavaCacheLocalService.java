package com.zhi.seckill.infrastructure.cache.local.guava;

import com.google.common.cache.Cache;
import com.zhi.seckill.infrastructure.cache.local.LocalCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author ZhiJH
 * @description 基于Guava实现本地缓存
 * @date 2024/5/22
 */
@Service
@ConditionalOnProperty(name = "local.cache.type", havingValue = "guava")
public class GuavaCacheLocalService<K, V> implements LocalCacheService<K, V> {

    private final Cache<K, V>  cache = LocalGuavaFactory.getLocalCache();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V getIfPresent(Object key) {
        return cache.getIfPresent(key);
    }
}
