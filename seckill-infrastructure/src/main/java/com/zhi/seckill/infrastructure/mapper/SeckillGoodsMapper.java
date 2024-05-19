package com.zhi.seckill.infrastructure.mapper;

import com.zhi.seckill.domain.model.SeckillGoods;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author ZhiJH
 * @description 商品信息
 * @date 2024/5/18
 */
public interface SeckillGoodsMapper {
    /**
     * 保存商品信息
     */
    int saveSeckillGoods(SeckillGoods seckillGoods);

    /**
     * 根据商品id获取商品信息
     */
    SeckillGoods getSeckillGoodsById(@Param("id") Long id);

    /**
     * 根据活动id获取商品列表
     */
    List<SeckillGoods> getSeckillGoodsByActivityId(@Param("activityId") Long activityId);

    /**
     * 修改商品id
     */
    int updateStatus(@Param("status") Integer status, @Param("id") Long id);

    /**
     * 扣减库存
     */
    int updateAvailableStock(@Param("count") Integer count, @Param("id") Long id);

    /**
     * 获取当前可用库存
     */
    Integer getAvailableStockById(@Param("id") Long id);
}
