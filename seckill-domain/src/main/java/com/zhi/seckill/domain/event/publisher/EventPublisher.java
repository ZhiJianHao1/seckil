package com.zhi.seckill.domain.event.publisher;

import com.alibaba.cola.event.DomainEventI;

/**
 * @author ZhiJH
 * @description 事件发布器
 * @date 2024/5/23
 */

public interface EventPublisher {
    /**
     * 发布事件
     */
    void publish(DomainEventI domainEvent);
}
