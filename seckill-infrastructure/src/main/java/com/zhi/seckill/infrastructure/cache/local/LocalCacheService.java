package com.zhi.seckill.infrastructure.cache.local;

/**
 * @author ZhiJH
 * @description 本地缓存服务接口
 * @date 2024/5/22
 */
public interface LocalCacheService<K, V> {

    void put(K key, V value);

    V getIfPresent(Object key);
}
