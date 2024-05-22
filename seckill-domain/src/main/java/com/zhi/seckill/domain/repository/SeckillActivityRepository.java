package com.zhi.seckill.domain.repository;

import com.zhi.seckill.domain.model.entity.SeckillActivity;

import java.util.Date;
import java.util.List;

/**
 * @author ZhiJH
 * @description 活动
 * @date 2024/5/15
 */
public interface SeckillActivityRepository {

    /**
     * 保存秒杀活动信息
     */
    int saveSeckillActivity(SeckillActivity seckillActivity);

    /**
     * 保存秒杀活动信息
     */
    List<SeckillActivity> getSeckillActivityList(Integer status);

    /**
     * 保存秒杀活动信息
     */
    List<SeckillActivity> getSeckillActivityByBetweenStartTimeAndEndTime(Date currentTime, Integer status);

    /**
     * 根据id获取秒杀活动信息
     */
    SeckillActivity getSeckillActivityById(Long id);

    /**
     *  根据id获取秒杀活动信息
     */
    int updateStatus(Integer status, Long id);
}
