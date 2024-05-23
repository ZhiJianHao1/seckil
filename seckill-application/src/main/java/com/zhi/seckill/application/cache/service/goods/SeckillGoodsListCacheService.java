package com.zhi.seckill.application.cache.service.goods;

import com.zhi.seckill.application.cache.model.SeckillBusinessCache;
import com.zhi.seckill.application.cache.service.common.SeckillCacheService;
import com.zhi.seckill.domain.model.entity.SeckillGoods;

import java.util.List;

/**
 * @author ZhiJH
 * @description 商品缓存服务接口
 * @date 2024/5/23
 */
public interface SeckillGoodsListCacheService extends SeckillCacheService {

    /**
     * 获取缓存中的支付列表
     */
    SeckillBusinessCache<List<SeckillGoods>> getCachedGoodsList(Long activityId, Long version);

    /**
     * 更新缓存数据
     */
    SeckillBusinessCache<List<SeckillGoods>> tryUpdateSeckillGoodsCacheByLock(Long activityId);
}
