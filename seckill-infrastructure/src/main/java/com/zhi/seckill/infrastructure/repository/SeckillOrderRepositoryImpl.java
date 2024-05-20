package com.zhi.seckill.infrastructure.repository;

import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.exception.SeckillException;
import com.zhi.seckill.domain.model.SeckillOrder;
import com.zhi.seckill.domain.repository.SeckillOrderRepository;
import com.zhi.seckill.infrastructure.mapper.SeckillOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhiJH
 * @description 订单
 * @date 2024/5/20
 */
@Component
public class SeckillOrderRepositoryImpl implements SeckillOrderRepository {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    public int saveSeckillOrder(SeckillOrder seckillOrder) {
        if (seckillOrder == null) {
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        return seckillOrderMapper.saveSeckillOrder(seckillOrder);
    }

    @Override
    public List<SeckillOrder> getSeckillOrderByUserId(Long userId) {
        return seckillOrderMapper.getSeckillOrderByUserId(userId);
    }

    @Override
    public List<SeckillOrder> getSeckillOrderByActivityId(Long activityId) {
        return seckillOrderMapper.getSeckillOrderByActivityId(activityId);
    }
}
