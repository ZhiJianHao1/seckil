package com.zhi.seckill.application.cache.service.activity.impl;

import com.alibaba.fastjson.JSON;
import com.zhi.seckill.application.builder.SeckillActivityBuilder;
import com.zhi.seckill.application.cache.model.SeckillBusinessCache;
import com.zhi.seckill.application.cache.service.activity.SeckillActivityCacheService;
import com.zhi.seckill.domain.constants.SeckillConstants;
import com.zhi.seckill.domain.model.entity.SeckillActivity;
import com.zhi.seckill.domain.repository.SeckillActivityRepository;
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
public class SeckillActivityCachedServiceImpl implements SeckillActivityCacheService {

    private final static Logger logger = LoggerFactory.getLogger(SeckillActivityCachedServiceImpl.class);

    @Autowired
    private LocalCacheService<Long, SeckillBusinessCache<SeckillActivity>> localCacheService;
    // 更新活动时获取分布式锁使用
    private static final String SECKILL_ACTIVITY_UPDATE_CACHE_LOCK_KEY = "SECKILL_ACTIVITY_UPDATE_CACHE_LOCK_KEY_";
    // 本地可重入锁
    private final Lock localCacheUpdatelock = new ReentrantLock();

    @Autowired
    private DistributedCacheService distributedCacheService;
    @Autowired
    private SeckillActivityRepository seckillActivityRepository;
    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Override
    public String buildCacheKey(Object key) {
        return StringUtil.append(SeckillConstants.SECKILL_ACTIVITY_CACHE_KEY, key);
    }


    @Override
    public SeckillBusinessCache<SeckillActivity> getCachedSeckillActivity(Long activityId, Long version) {
        // 先从本地缓存获取数据
        SeckillBusinessCache<SeckillActivity> seckillCacheActivity = localCacheService.getIfPresent(activityId);
        if (seckillCacheActivity != null) {
            //传递的版本号为空，则直接返回本地缓存中的数据
            if (version == null) {
                logger.info("SeckillActivityCache 命中本地缓存|{}", activityId);
                return seckillCacheActivity;
            }
            //传递的版本号小于等于缓存中的版本号，则说明缓存中的数据比客户端的数据新，直接返回本地缓存中的数据
            if (version.compareTo(seckillCacheActivity.getVersion()) <= 0) {
                logger.info("SeckillActivityCache 命中本地缓存|{}", activityId);
                return seckillCacheActivity;
            }
            //传递的版本号大于缓存中的版本号，说明缓存中的数据比较落后，从分布式缓存获取数据并更新到本地缓存
            if (version.compareTo(seckillCacheActivity.getVersion()) > 0) {
                return getDistributeCacheActivity(activityId);
            }
        }
        //从分布式缓存中获取数据，并设置到本地缓存中
        return getDistributeCacheActivity(activityId);
    }

    /**
     * 从分布式缓存中获取数据
     */
    private SeckillBusinessCache<SeckillActivity> getDistributeCacheActivity(Long activityId) {
        logger.info("SeckillActivityCache 读取分布式缓存数据|{}", activityId);
        SeckillBusinessCache<SeckillActivity> seckillBusinessCache = SeckillActivityBuilder.getSeckillBusinessCache(distributedCacheService.getObject(buildCacheKey(activityId)), SeckillActivity.class);
        if (seckillBusinessCache == null) {
            // 尝试更新分布式缓存数据，注意这里只有一个线程能够更新分布式缓存数据
            seckillBusinessCache = tryUpdateStockActivityCacheByLock(activityId, true);
        }
        if (seckillBusinessCache != null && !seckillBusinessCache.isRetryLater()) {
            // 获取本地锁，更新本地缓存数据
            boolean localLockSuccess = localCacheUpdatelock.tryLock();
            if (localLockSuccess) {
                try {
                    localCacheService.put(activityId, seckillBusinessCache);
                    logger.info("SeckillActivityCache|本地缓存已更新|{}", activityId);
                } finally {
                    localCacheUpdatelock.unlock();
                }
            }
        }
        return seckillBusinessCache;
    }


    @Override
    public SeckillBusinessCache<SeckillActivity> tryUpdateStockActivityCacheByLock(Long activityId, boolean doubleCheck) {
        logger.info("SeckillActivityCache 更新分布式缓存数据|{}", activityId);
        // 获取分布式锁
        DistributedLock lock = distributedLockFactory.getDistributedLock(SECKILL_ACTIVITY_UPDATE_CACHE_LOCK_KEY.concat(String.valueOf(activityId)));
        try {
            boolean isLockSuccess = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if (!isLockSuccess) {
                return new SeckillBusinessCache<SeckillActivity>().retryLater();
            }
            SeckillBusinessCache<SeckillActivity> seckillBusinessCacheActivity;
            if (doubleCheck) {
                seckillBusinessCacheActivity = SeckillActivityBuilder.getSeckillBusinessCache(distributedCacheService.getObject(buildCacheKey(activityId)), SeckillActivity.class);
                if (seckillBusinessCacheActivity != null) {
                    return seckillBusinessCacheActivity;
                }
            }
            SeckillActivity seckillActivityById = seckillActivityRepository.getSeckillActivityById(activityId);
            if (seckillActivityById == null) {
                seckillBusinessCacheActivity = new SeckillBusinessCache<SeckillActivity>().notExist();
            } else {
                seckillBusinessCacheActivity = new SeckillBusinessCache<SeckillActivity>().with(seckillActivityById).withVersion(SystemClock.millisClock().now());
            }
            //将数据放入分布式缓存
            distributedCacheService.put(buildCacheKey(activityId), JSON.toJSONString(seckillBusinessCacheActivity), SeckillConstants.FIVE_SECONDS);
            logger.info("SeckillActivityCache|分布式缓存已经更新|{}", activityId);
            // 返回数据
            return seckillBusinessCacheActivity;
        } catch (InterruptedException e) {
            logger.error("SeckillActivityCache|更新分布式缓存失败|{}", activityId);
            return new SeckillBusinessCache<SeckillActivity>().retryLater();
        } finally {
            lock.unlock();
        }
    }

}
