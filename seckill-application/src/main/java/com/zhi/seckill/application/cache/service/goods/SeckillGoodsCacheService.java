package com.zhi.seckill.application.cache.service.goods;

import com.zhi.seckill.application.cache.model.SeckillBusinessCache;
import com.zhi.seckill.application.cache.service.common.SeckillCacheService;
import com.zhi.seckill.domain.model.entity.SeckillGoods;

/**
 * @author ZhiJH
 * @description 商品缓存服务接口
 * @date 2024/5/23
 */
public interface SeckillGoodsCacheService extends SeckillCacheService {

    /**
     * 获取商品信息
     */
    SeckillBusinessCache<SeckillGoods> getSeckillGoods(Long goodsId, Long version);

    /**
     * 更新缓存
     */
    SeckillBusinessCache<SeckillGoods> tryUpdateSeckillGoodsCacheByLock(Long goodsId);

}
