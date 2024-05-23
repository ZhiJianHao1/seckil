package com.zhi.seckill.application.service;

import com.zhi.seckill.application.common.SeckillActivityCommand;
import com.zhi.seckill.domain.model.dto.SeckillActivityDTO;
import com.zhi.seckill.domain.model.entity.SeckillActivity;

import java.util.Date;
import java.util.List;

/**
 * @author ZhiJH
 * @description 秒杀活动信息
 * @date 2024/5/15
 */
public interface SeckillActivityService {
    /**
     * 保存活动信息
     */
    void saveSeckillActivityDTO(SeckillActivityCommand seckillActivityCommand);

    /**
     * 根据状态获取活动列表
     */
    List<SeckillActivity> getSeckillActivityList(Integer status);

    /**
     * 根据时间和状态获取活动列表
     */
    List<SeckillActivity> getSeckillActivityListBetweenStartTimeAndEndTime(Date currentTime, Integer status);

    /**
     * 根据id获取活动信息
     */
    SeckillActivity getSeckillActivityById(Long id);

    /**
     * 修改状态
     */
    int updateStatus(Integer status, Long id);

    /**
     * 活动列表
     */
    List<SeckillActivityDTO> getSeckillActivityList(Integer status, Long version);

    /**
     * 获取活动信息，带有缓存
     */
    SeckillActivityDTO getSeckillActivity(Long id, Long version);
}
