package com.zhi.seckill.infrastructure.repository;

import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.exception.SeckillException;
import com.zhi.seckill.domain.model.entity.SeckillActivity;
import com.zhi.seckill.domain.repository.SeckillActivityRepository;
import com.zhi.seckill.infrastructure.mapper.SeckillActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author ZhiJH
 * @description 秒杀活动
 * @date 2024/5/15
 */
@Component
public class SeckillActivityRepositoryImpl implements SeckillActivityRepository {

    @Autowired
    private SeckillActivityMapper seckillActivityMapper;

    @Override
    public int saveSeckillActivity(SeckillActivity seckillActivity) {
        if (seckillActivity == null) {
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        return seckillActivityMapper.saveSeckillActivity(seckillActivity);
    }

    @Override
    public List<SeckillActivity> getSeckillActivityList(Integer status) {
        return seckillActivityMapper.getSeckillActivityList(status);
    }

    @Override
    public List<SeckillActivity> getSeckillActivityByBetweenStartTimeAndEndTime(Date currentTime, Integer status) {
        return seckillActivityMapper.getSeckillActivityByBetweenStartTimeAndEndTime(currentTime, status);
    }

    @Override
    public SeckillActivity getSeckillActivityById(Long id) {
        return seckillActivityMapper.getSeckillActivityById(id);
    }

    @Override
    public int updateStatus(Integer status, Long id) {
        return seckillActivityMapper.updateStatus(status, id);
    }
}
