package com.zhi.seckill.application.cache.service.activity;

import com.zhi.seckill.application.cache.model.SeckillBusinessCache;
import com.zhi.seckill.application.cache.service.common.SeckillCacheService;
import com.zhi.seckill.domain.model.entity.SeckillActivity;

import java.util.List;

/**
 * @author ZhiJH
 * @description 加有缓存的秒杀活动服务接口
 * @date 2024/5/22
 */
public interface SeckillActivityListCacheService extends SeckillCacheService {

    /**
     * 增加二级缓存 根据状态获取活动列表
     */
    SeckillBusinessCache<List<SeckillActivity>> getCacheActivities(Integer status, Long version);

    /**
     * 更新缓存数据
     */
    SeckillBusinessCache<List<SeckillActivity>> tryUpdateSeckillActivityCacheByLock(Integer status, boolean doubleCheck);
}
