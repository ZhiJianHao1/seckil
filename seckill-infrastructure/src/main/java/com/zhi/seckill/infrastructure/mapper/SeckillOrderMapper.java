package com.zhi.seckill.infrastructure.mapper;

import com.zhi.seckill.domain.model.entity.SeckillOrder;

import java.util.List;

/**
 * @author ZhiJH
 * @description
 * @date 2024/5/20
 */
public interface SeckillOrderMapper {
    /**
     * 保存订单
     */
    int saveSeckillOrder(SeckillOrder seckillOrder);

    /**
     * 根据用户id获取订单列表
     */
    List<SeckillOrder> getSeckillOrderByUserId(Long userId);

    /**
     * 根据活动id获取订单列表
     */
    List<SeckillOrder> getSeckillOrderByActivityId(Long activityId);
}
