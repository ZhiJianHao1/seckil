package com.zhi.seckill.domain.event.publisher;

import com.alibaba.cola.event.DomainEventI;
import com.alibaba.cola.event.EventBusI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZhiJH
 * @description 本地事件发布
 * @date 2024/5/23
 */
@Component
public class LocalDomainEventPublisher implements EventPublisher{

    @Autowired
    private EventBusI eventBusI;

    @Override
    public void publish(DomainEventI domainEvent) {
        eventBusI.fire(domainEvent);
    }
}
