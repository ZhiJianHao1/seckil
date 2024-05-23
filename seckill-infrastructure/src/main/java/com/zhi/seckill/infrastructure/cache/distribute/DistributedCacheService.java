package com.zhi.seckill.infrastructure.cache.distribute;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhiJH
 * @description 分布式缓存接口
 * @date 2024/5/22
 */
public interface DistributedCacheService {

    void put(String key, String value);

    void put(String key, Object value);

    void put(String key, Object value, long timeout, TimeUnit unit);

    void put(String key, Object value, long expireTime);

    <T> T getObject(String key, Class<T> tragetClass);

    String getString(String key);

    <T> List<T> getList(String key, Class<T> tragetClass);

    Boolean delete(String key);

    Boolean hasKey(String key);

    Object getObject(String key);
}
