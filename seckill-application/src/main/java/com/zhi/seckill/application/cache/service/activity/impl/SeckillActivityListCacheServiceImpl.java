package com.zhi.seckill.application.cache.service.activity.impl;

import com.alibaba.fastjson.JSON;
import com.zhi.seckill.application.builder.SeckillActivityBuilder;
import com.zhi.seckill.application.cache.model.SeckillBusinessCache;
import com.zhi.seckill.application.cache.service.activity.SeckillActivityListCacheService;
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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhiJH
 * @description 秒杀活动Service实现类
 * @date 2024/5/22
 */
@Service
public class SeckillActivityListCacheServiceImpl implements SeckillActivityListCacheService {

    private final static Logger logger = LoggerFactory.getLogger(SeckillActivityListCacheServiceImpl.class);

    @Autowired
    private LocalCacheService<Long, SeckillBusinessCache<List<SeckillActivity>>> localCacheService;
    // 分布式锁的Key
    private static final String SECKILL_ACTIVITES_UPDATE_CACHE_LOCK_KEY = "SECKILL_ACTIVITIES_UPDATE_CACHE_LOCK_KEY_";
    // 本地锁
    private final Lock localCacheUpdatelock = new ReentrantLock();

    @Autowired
    private DistributedCacheService distributedCacheService;
    @Autowired
    private SeckillActivityRepository seckillActivityRepository;
    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Override
    public String buildCacheKey(Object key) {
        return StringUtil.append(SeckillConstants.SECKILL_ACTIVITIES_CACHE_KEY, key);
    }

    @Override
    public SeckillBusinessCache<List<SeckillActivity>> getCacheActivities(Integer status, Long version) {
        SeckillBusinessCache<List<SeckillActivity>> seckillActivitiesListCache = localCacheService.getIfPresent(status.longValue());
        if (seckillActivitiesListCache != null) {
            if (version == null) {
                logger.info("SeckillActivitiesCache|命中本地缓存: {} ", status);
                return seckillActivitiesListCache;
            }
            // 传递过来的版本号小于或者等于缓存中的版本号
            if (version.compareTo(seckillActivitiesListCache.getVersion()) <= 0) {
                logger.info("SeckillActivitiesCache|命中本地缓存: {} ", status);
                return seckillActivitiesListCache;
            }
            if (version.compareTo(seckillActivitiesListCache.getVersion()) > 0) {
                return getDistributedCache(status);
            }
        }
        return getDistributedCache(status);
    }

    /**
     * 获取分布式缓存中的数据
     */
    private SeckillBusinessCache<List<SeckillActivity>> getDistributedCache(Integer status) {
        logger.info("SeckillActivitesCache|读取分布式缓存|{}", status);
        SeckillBusinessCache<List<SeckillActivity>> seckillActivitiyListCache = SeckillActivityBuilder.getSeckillBusinessCacheList(distributedCacheService.getObject(buildCacheKey(status)),  SeckillActivity.class);
        if (seckillActivitiyListCache == null){
            seckillActivitiyListCache = tryUpdateSeckillActivityCacheByLock(status);
        }
        if (seckillActivitiyListCache != null && !seckillActivitiyListCache.isRetryLater()){
            if (localCacheUpdatelock.tryLock()){
                try {
                    localCacheService.put(status.longValue(), seckillActivitiyListCache);
                    logger.info("SeckillActivitesCache|本地缓存已经更新|{}", status);
                }finally {
                    localCacheUpdatelock.unlock();
                }
            }
        }
        return seckillActivitiyListCache;
    }

    /**
     * 根据状态更新分布式缓存数据
     */
    @Override
    public SeckillBusinessCache<List<SeckillActivity>> tryUpdateSeckillActivityCacheByLock(Integer status) {
        logger.info("SeckillActivitiesCache|更新分布式缓存|{}", status);
        DistributedLock lock = distributedLockFactory.getDistributedLock(SECKILL_ACTIVITES_UPDATE_CACHE_LOCK_KEY.concat(String.valueOf(status)));
        try {
            boolean isLockSuccess = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if (!isLockSuccess) {
                return new SeckillBusinessCache<List<SeckillActivity>>().retryLater();
            }
            List<SeckillActivity> seckillActivityList = seckillActivityRepository.getSeckillActivityList(status);
            SeckillBusinessCache<List<SeckillActivity>> seckillActivityListCache;
            if (seckillActivityList == null) {
                seckillActivityListCache = new SeckillBusinessCache<List<SeckillActivity>>().notExist();
            } else {
                seckillActivityListCache = new SeckillBusinessCache<List<SeckillActivity>>().with(seckillActivityList).withVersion(SystemClock.millisClock().now());
            }
            distributedCacheService.put(buildCacheKey(status), JSON.toJSONString(seckillActivityListCache), SeckillConstants.FIVE_MINUTES);
            logger.info("seckillActivityListCache|分布式缓存已经更新|{}", status);
            return seckillActivityListCache;
        } catch (InterruptedException e) {
            logger.info("seckillActivityListCache|分布式缓存已经更新失败|{}", status);
            return new SeckillBusinessCache<List<SeckillActivity>>().retryLater();
        } finally {
            lock.unlock();
        }
    }
}
