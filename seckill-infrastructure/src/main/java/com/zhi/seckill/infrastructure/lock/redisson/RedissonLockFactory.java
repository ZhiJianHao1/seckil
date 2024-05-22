package com.zhi.seckill.infrastructure.lock.redisson;

import com.zhi.seckill.infrastructure.lock.DistributedLock;
import com.zhi.seckill.infrastructure.lock.factory.DistributedLockFactory;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhiJH
 * @description 基于Redisson的分布式锁实现服务
 * @date 2024/5/22
 */
@Component
@ConditionalOnProperty(name = "distributed.lock.type", havingValue = "redisson")
public class RedissonLockFactory implements DistributedLockFactory {

    private final Logger logger = LoggerFactory.getLogger(RedissonLockFactory.class);

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public DistributedLock getDistributedLock(String key) {
        RLock rLock = redissonClient.getLock(key);
        return new DistributedLock() {
            @Override
            public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
                boolean isLockSuccess = rLock.tryLock(waitTime, leaseTime, unit);
                logger.info("{} get lock result : {}", key, isLockSuccess);
                return isLockSuccess;
            }

            @Override
            public void lock(long endTime, TimeUnit unit) {
                rLock.lock(endTime, unit);
            }

            @Override
            public void unlock() {
                if (isLocked() && isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }

            @Override
            public boolean isLocked() {
                return rLock.isLocked();
            }

            @Override
            public boolean isHeldByThread(long threadId) {
                return rLock.isHeldByThread(threadId);
            }

            @Override
            public boolean isHeldByCurrentThread() {
                return rLock.isHeldByCurrentThread();
            }
        };
    }
}
