package com.zhi.seckill.application.cache.service.activity;

import com.zhi.seckill.application.cache.model.SeckillBusinessCache;
import com.zhi.seckill.application.cache.service.common.SeckillCacheService;
import com.zhi.seckill.domain.model.entity.SeckillActivity;

/**
 * @author ZhiJH
 * @description 带有缓存的秒杀活动服务接口
 * @date 2024/5/23
 */
public interface SeckillActivityCacheService extends SeckillCacheService {
    /**
     * 根据id获取活动信息
     */
    SeckillBusinessCache<SeckillActivity> getCachedSeckillActivity(Long activityId, Long version);

    /**
     * 更新缓存数据
     */
    SeckillBusinessCache<SeckillActivity> tryUpdateStockActivityCacheByLock(Long activityId, boolean doubleCheck);
}
