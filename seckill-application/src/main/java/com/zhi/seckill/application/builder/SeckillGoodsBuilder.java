package com.zhi.seckill.application.builder;

import com.zhi.seckill.application.builder.common.SeckillCommonBuilder;
import com.zhi.seckill.application.common.SeckillGoodsCommand;
import com.zhi.seckill.domain.model.dto.SeckillGoodsDTO;
import com.zhi.seckill.domain.model.entity.SeckillGoods;
import com.zhi.seckill.infrastructure.utils.beans.BeanUtil;

/**
 * @author ZhiJH
 * @description
 * @date 2024/5/23
 */
public class SeckillGoodsBuilder extends SeckillCommonBuilder {
    public static SeckillGoods toSeckillGoods(SeckillGoodsCommand seckillGoodsCommand){
        if (seckillGoodsCommand == null){
            return null;
        }
        SeckillGoods seckillGoods = new SeckillGoods();
        BeanUtil.copyProperties(seckillGoodsCommand, seckillGoods);
        return seckillGoods;
    }

    public static SeckillGoodsDTO toSeckillGoodsDTO(SeckillGoods seckillGoods){
        if (seckillGoods == null){
            return null;
        }
        SeckillGoodsDTO seckillGoodsDTO = new SeckillGoodsDTO();
        BeanUtil.copyProperties(seckillGoods, seckillGoodsDTO);
        return seckillGoodsDTO;
    }
}
