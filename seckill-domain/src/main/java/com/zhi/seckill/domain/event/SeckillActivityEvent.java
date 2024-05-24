package com.zhi.seckill.domain.event;

/**
 * @author ZhiJH
 * @description 活动事件
 * @date 2024/5/24
 */
public class SeckillActivityEvent extends SeckillBaseEvent{

    public SeckillActivityEvent(Long id, Integer status) {
        super(id, status);
    }
}
