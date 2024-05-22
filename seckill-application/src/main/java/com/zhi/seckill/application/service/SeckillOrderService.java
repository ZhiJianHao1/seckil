package com.zhi.seckill.application.service;

import com.zhi.seckill.domain.model.dto.SeckillOrderDTO;
import com.zhi.seckill.domain.model.entity.SeckillOrder;

import java.util.List;

/**
 * @author ZhiJH
 * @description 订单信息
 * @date 2024/5/20
 */
public interface SeckillOrderService {
    /**
     * 保存订单
     */
    SeckillOrder saveSeckillOrder(SeckillOrderDTO seckillOrderDTO);

    /**
     * 根据用户id获取订单列表
     */
    List<SeckillOrder> getSeckillOrderByUserId(Long userId);

    /**
     * 根据活动id获取订单列表
     */
    List<SeckillOrder> getSeckillOrderByActivityId(Long activityId);
}
