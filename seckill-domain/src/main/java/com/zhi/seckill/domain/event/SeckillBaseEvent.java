package com.zhi.seckill.domain.event;

import com.alibaba.cola.event.DomainEventI;

/**
 * @author ZhiJH
 * @description 事件基础模型
 * @date 2024/5/23
 */
public class SeckillBaseEvent implements DomainEventI {
    private Long id;
    private Integer status;

    public SeckillBaseEvent(Long id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
