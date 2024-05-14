package com.zhi.seckill.application.service;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhiJH
 * @description Redis缓存接口
 * @date 2024/5/13
 */
public interface RedisService {
    /**
     * 设置缓存
     */
    void set(String key, Object value);

    /**
     * 设置缓存并设置超时时间
     */
    void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 从缓存中获取数据
     */
    Object get(String key);
}
