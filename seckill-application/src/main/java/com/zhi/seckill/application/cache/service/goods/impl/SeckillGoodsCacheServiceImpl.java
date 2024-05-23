package com.zhi.seckill.application.cache.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.zhi.seckill.application.builder.SeckillGoodsBuilder;
import com.zhi.seckill.application.cache.model.SeckillBusinessCache;
import com.zhi.seckill.application.cache.service.goods.SeckillGoodsCacheService;
import com.zhi.seckill.domain.constants.SeckillConstants;
import com.zhi.seckill.domain.model.entity.SeckillGoods;
import com.zhi.seckill.domain.repository.SeckillGoodsRepository;
import com.zhi.seckill.infrastructure.cache.distribute.DistributedCacheService;
import com.zhi.seckill.infrastructure.cache.local.LocalCacheService;
import com.zhi.seckill.infrastructure.lock.DistributedLock;
import com.zhi.seckill.infrastructure.lock.factory.DistributedLockFactory;
import com.zhi.seckill.infrastructure.utils.string.StringUtil;
import com.zhi.seckill.infrastructure.utils.time.SystemClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhiJH
 * @description
 * @date 2024/5/23
 */
@Service
public class SeckillGoodsCacheServiceImpl implements SeckillGoodsCacheService {

    private final static Logger logger = LoggerFactory.getLogger(SeckillGoodsCacheServiceImpl.class);

    @Autowired
    private LocalCacheService<Long, SeckillBusinessCache<SeckillGoods>> localCacheService;
    //更新活动时获取分布式锁使用
    private static final String SECKILL_GOODS_UPDATE_CACHE_LOCK_KEY = "SECKILL_GOODS_UPDATE_CACHE_LOCK_KEY_";
    //本地可重入锁
    private final Lock localCacheUpdatelock = new ReentrantLock();
    @Autowired
    private DistributedCacheService distributedCacheService;
    @Autowired
    private SeckillGoodsRepository seckillGoodsRepository;
    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Override
    public SeckillBusinessCache<SeckillGoods> getSeckillGoods(Long goodsId, Long version) {
        SeckillBusinessCache<SeckillGoods> goodsSeckillBusinessCache = localCacheService.getIfPresent(goodsId);
        if (goodsSeckillBusinessCache != null) {
            if (version == null) {
                logger.info("SeckillGoodsCache|命中本地缓存|{}", goodsId);
                return goodsSeckillBusinessCache;
            }
            if (version.compareTo(goodsSeckillBusinessCache.getVersion()) <= 0) {
                logger.info("SeckillGoodsCache|命中本地缓存|{}", goodsId);
                return goodsSeckillBusinessCache;
            }
            if (version.compareTo(goodsSeckillBusinessCache.getVersion()) > 0) {
                return getDistributedCache(goodsId);
            }
        }
        return getDistributedCache(goodsId);
    }

    private SeckillBusinessCache<SeckillGoods> getDistributedCache(Long goodsId) {
        SeckillBusinessCache<SeckillGoods> seckillBusinessCache = SeckillGoodsBuilder.getSeckillBusinessCache(distributedCacheService.getObject(buildCacheKey(goodsId)), SeckillGoods.class);
        if (seckillBusinessCache == null) {
            seckillBusinessCache = tryUpdateSeckillGoodsCacheByLock(goodsId);
        }
        if (seckillBusinessCache != null && !seckillBusinessCache.isRetryLater()) {
           if (localCacheUpdatelock.tryLock()) {
               try {
                   localCacheService.put(goodsId, seckillBusinessCache);
                   logger.info("SeckillGoodsCache|本地缓存已经更新|{}", goodsId);
               } finally {
                   localCacheUpdatelock.unlock();
               }
           }
        }
        return seckillBusinessCache;
    }

    @Override
    public SeckillBusinessCache<SeckillGoods> tryUpdateSeckillGoodsCacheByLock(Long goodsId) {
        logger.info("SeckillGoodsCache|更新分布式缓存|{}", goodsId);
        //获取分布式锁，保证只有一个线程在更新分布式缓存
        DistributedLock lock = distributedLockFactory.getDistributedLock(SECKILL_GOODS_UPDATE_CACHE_LOCK_KEY.concat(String.valueOf(goodsId)));
        try {
            boolean isSuccess = lock.tryLock(2, 5, TimeUnit.SECONDS);
            //未获取到分布式锁的线程快速返回，不占用系统资源
            if (!isSuccess){
                return new SeckillBusinessCache<SeckillGoods>().retryLater();
            }
            SeckillGoods seckillGoods = seckillGoodsRepository.getSeckillGoodsById(goodsId);
            SeckillBusinessCache<SeckillGoods> seckillGoodsCache;
            if (seckillGoods == null){
                seckillGoodsCache = new SeckillBusinessCache<SeckillGoods>().notExist();
            }else {
                seckillGoodsCache = new SeckillBusinessCache<SeckillGoods>().with(seckillGoods).withVersion(SystemClock.millisClock().now());
            }
            //将数据保存到分布式缓存
            distributedCacheService.put(buildCacheKey(goodsId), JSON.toJSONString(seckillGoodsCache), SeckillConstants.FIVE_MINUTES);
            logger.info("SeckillGoodsCache|分布式缓存已经更新|{}", goodsId);
            return seckillGoodsCache;
        } catch (InterruptedException e) {
            logger.error("SeckillGoodsCache|更新分布式缓存失败|{}", goodsId);
            return new SeckillBusinessCache<SeckillGoods>().retryLater();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public String buildCacheKey(Object key) {
        return StringUtil.append(SeckillConstants.SECKILL_GOODS_CACHE_KEY, key);
    }
}
