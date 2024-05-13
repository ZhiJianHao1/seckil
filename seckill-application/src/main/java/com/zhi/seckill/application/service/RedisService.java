package com.zhi.seckill.application.service;

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
}
