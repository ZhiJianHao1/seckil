package com.zhi.seckill.application.service.impl;

import com.zhi.seckill.application.service.SeckillGoodsService;
import com.zhi.seckill.application.service.SeckillOrderService;
import com.zhi.seckill.domain.dto.SeckillOrderDTO;
import com.zhi.seckill.domain.model.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author ZhiJH
 * @description 订单
 * @date 2024/5/20
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private SeckillOrderService seckillOrderRepository;


    @Override
    public SeckillOrder saveSeckillOrder(SeckillOrderDTO seckillOrderDTO) {
        return null;
    }

    @Override
    public List<SeckillOrder> getSeckillOrderByUserId(Long userId) {
        return seckillOrderRepository.getSeckillOrderByUserId(userId);
    }

    @Override
    public List<SeckillOrder> getSeckillOrderByActivityId(Long activityId) {
        return seckillOrderRepository.getSeckillOrderByActivityId(activityId);
    }
}
