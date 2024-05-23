package com.zhi.seckill.application.service;

import com.zhi.seckill.application.common.SeckillGoodsCommand;
import com.zhi.seckill.domain.model.dto.SeckillGoodsDTO;
import com.zhi.seckill.domain.model.entity.SeckillGoods;

import java.util.List;

/**
 * @author ZhiJH
 * @description 秒杀商品信息
 * @date 2024/5/18
 */
public interface SeckillGoodsService {

    /**
     * 保存商品信息
     */
    int saveSeckillGoods(SeckillGoodsCommand seckillGoodsCommand);

    /**
     * 根据id获取商品信息
     */
    SeckillGoods getSeckillGoodsId(Long id);

    /**
     * 根据活动id获取商品列表
     */
    List<SeckillGoods> getSeckillGoodsByActivityId(Long activityId);

    /**
     * 修改商品信息
     */
    int updateStatus(Integer status, Long id);

    /**
     * 扣减库存
     */
    int updateAvailableStock(Integer count, Long id);

    /**
     * 获取当前可用库存
     */
    Integer getAvailableStockById(Long id);

    /**
     * 根据活动id从缓存中获取数据
     */
    List<SeckillGoodsDTO> getSeckillGoodsList(Long activityId, Long version);
}
