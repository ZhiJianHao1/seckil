package com.zhi.seckill.infrastructure.mapper;

import com.zhi.seckill.domain.model.entity.SeckillActivity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author ZhiJH
 * @description 秒杀活动
 * @date 2024/5/15
 */
public interface SeckillActivityMapper {
    /**
     * 保存秒杀活动信息
     */
    int saveSeckillActivity(SeckillActivity seckillActivity);

    /**
     *  根据状态获取秒杀活动信息列表
     */
    List<SeckillActivity> getSeckillActivityList(@Param("status") Integer status);

    /**
     *  根据状态获取秒杀活动信息列表
     */
    List<SeckillActivity> getSeckillActivityByBetweenStartTimeAndEndTime(@Param("currentTime") Date currentTime, @Param("status") Integer status);
    /**
     * 根据id获取秒杀活动信息
     */
    SeckillActivity getSeckillActivityById(@Param("id") Long id);

    /**
     * 根据id获取秒杀活动信息
     */
    int updateStatus(@Param("status") Integer status, @Param("id") Long id);
}
