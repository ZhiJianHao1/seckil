package com.zhi.seckill.application.cache.service.common;

/**
 * @author ZhiJH
 * @description 通用缓存接口
 * @date 2024/5/22
 */
public interface SeckillCacheService {

    /**
     * 构建缓存的Key
     */
    String buildCacheKey(Object key);
}
