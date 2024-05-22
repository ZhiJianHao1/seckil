package com.zhi.seckill.infrastructure.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhiJH
 * @description 分布式锁接口
 * @date 2024/5/22
 */
public interface DistributedLock {

    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    void lock(long endTime, TimeUnit unit);

    void unlock();

    boolean isLocked();

    boolean isHeldByThread(long threadId);

    boolean isHeldByCurrentThread();
}
