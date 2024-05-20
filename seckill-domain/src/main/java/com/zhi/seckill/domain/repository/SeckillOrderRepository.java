package com.zhi.seckill.domain.repository;

import com.zhi.seckill.domain.model.SeckillOrder;

import java.util.List;

/**
 * @author ZhiJH
 * @description 订单
 * @date 2024/5/20
 */
public interface SeckillOrderRepository {
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
