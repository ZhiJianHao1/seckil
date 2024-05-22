package com.zhi.seckill.application.service.impl;

import com.zhi.seckill.application.service.SeckillGoodsService;
import com.zhi.seckill.application.service.SeckillOrderService;
import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.model.dto.SeckillOrderDTO;
import com.zhi.seckill.domain.model.enums.SeckillGoodsStatus;
import com.zhi.seckill.domain.model.enums.SeckillOrderStatus;
import com.zhi.seckill.domain.exception.SeckillException;
import com.zhi.seckill.domain.model.entity.SeckillGoods;
import com.zhi.seckill.domain.model.entity.SeckillOrder;
import com.zhi.seckill.domain.repository.SeckillOrderRepository;
import com.zhi.seckill.infrastructure.utils.beans.BeanUtil;
import com.zhi.seckill.infrastructure.utils.id.SnowFlakeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
    private SeckillOrderRepository seckillOrderRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeckillOrder saveSeckillOrder(SeckillOrderDTO seckillOrderDTO) {
        if (seckillOrderDTO == null) {
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        // 获取商品
        SeckillGoods seckillGoods = seckillGoodsService.getSeckillGoodsId(seckillOrderDTO.getGoodsId());
        // 商品不存在
        if (seckillGoods == null) {
            throw new SeckillException(HttpCode.GOODS_NOT_EXISTS);
        }
        // 商品未上线
        if (seckillGoods.getStatus() == SeckillGoodsStatus.PUBLISHED.getCode()) {
            throw new SeckillException(HttpCode.GOODS_PUBLISH);
        }
        // 商品已下架
        if (seckillGoods.getStatus() == SeckillGoodsStatus.OFFLINE.getCode()) {
            throw new SeckillException(HttpCode.GOODS_OFFLINE);
        }
        // 触发限购
        if (seckillGoods.getLimitNum() < seckillOrderDTO.getQuantity()) {
            throw new SeckillException(HttpCode.BEYOND_LIMIT_NUM);
        }
        // 库存不足
        if (seckillGoods.getAvailableStock() == null || seckillGoods.getAvailableStock() == 0 || seckillOrderDTO.getQuantity() > seckillGoods.getAvailableStock()) {
            throw new SeckillException(HttpCode.STOCK_LT_ZERO);
        }
        
        SeckillOrder seckillOrder = new SeckillOrder();
        BeanUtil.copyProperties(seckillOrderDTO, seckillOrder);
        seckillOrder.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
        seckillOrder.setGoodsName(seckillGoods.getGoodsName());
        seckillOrder.setActivityPrice(seckillGoods.getActivityPrice());
        BigDecimal orderPrice = seckillGoods.getActivityPrice().multiply(BigDecimal.valueOf(seckillOrder.getQuantity()));
        seckillOrder.setOrderPrice(orderPrice);
        seckillOrder.setStatus(SeckillOrderStatus.CREATED.getCode());
        seckillOrder.setCreateTime(new Date());

        // 保存订单
        seckillOrderRepository.saveSeckillOrder(seckillOrder);
        // 扣减库存
        seckillGoodsService.updateAvailableStock(seckillOrderDTO.getQuantity(), seckillOrderDTO.getGoodsId());
        return seckillOrder;
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
