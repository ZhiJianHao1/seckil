package com.zhi.seckill.infrastructure.lock.factory;

import com.zhi.seckill.infrastructure.lock.DistributedLock;

/**
 * @author ZhiJH
 * @description 分布式锁工厂
 * @date 2024/5/22
 */
public interface DistributedLockFactory {
    /**
     * 根据Key获取分布式锁
     */
    DistributedLock getDistributedLock(String key);
}
