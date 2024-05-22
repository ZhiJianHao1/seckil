package com.zhi.seckill.domain.repository;

import com.zhi.seckill.domain.model.entity.SeckillGoods;

import java.util.List;

/**
 * @author ZhiJH
 * @description 商品
 * @date 2024/5/18
 */
public interface SeckillGoodsRepository {
    /**
     * 保存商品信息
     */
    int saveSeckillGoods(SeckillGoods seckillGoods);

    /**
     * 根据商品id获取商品信息
     */
    SeckillGoods getSeckillGoodsById(Long id);

    /**
     * 根据活动id获取商品列表
     */
    List<SeckillGoods> getSeckillGoodsByActivityId(Long activityId);

    /**
     * 修改商品状态
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
}
