package com.zhi.seckill.application.service.impl;

import com.zhi.seckill.application.service.SeckillActivityService;
import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.dto.SeckillActivityDTO;
import com.zhi.seckill.domain.enums.SeckillActivityStatus;
import com.zhi.seckill.domain.exception.SeckillException;
import com.zhi.seckill.domain.model.SeckillActivity;
import com.zhi.seckill.domain.repository.SeckillActivityRepository;
import com.zhi.seckill.infrastructure.utils.beans.BeanUtil;
import com.zhi.seckill.infrastructure.utils.id.SnowFlakeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ZhiJH
 * @description 秒杀活动
 * @date 2024/5/15
 */
@Service
public class SeckillActivityServiceImpl implements SeckillActivityService {

    @Autowired
    private SeckillActivityRepository seckillActivityRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSeckillActivityDTO(SeckillActivityDTO seckillActivityDTO) {
        if (seckillActivityDTO == null) {
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        SeckillActivity seckillActivity = new SeckillActivity();
        BeanUtil.copyProperties(seckillActivityDTO, seckillActivity);
        seckillActivity.setId(SnowFlakeFactory.getSnowFlake().nextId());
        seckillActivity.setStatus(SeckillActivityStatus.PUBLISHED.getCode());
        seckillActivityRepository.saveSeckillActivity(seckillActivity);
    }

    @Override
    public List<SeckillActivity> getSeckillActivityList(Integer status) {
        return seckillActivityRepository.getSeckillActivityList(status);
    }

    @Override
    public List<SeckillActivity> getSeckillActivityListBetweenStartTimeAndEndTime(Date currentTime, Integer status) {
        return seckillActivityRepository.getSeckillActivityByBetweenStartTimeAndEndTime(currentTime, status);
    }

    @Override
    public SeckillActivity getSeckillActivityById(Long id) {
        return seckillActivityRepository.getSeckillActivityById(id);
    }

    @Override
    public int updateStatus(Integer status, Long id) {
        return seckillActivityRepository.updateStatus(status, id);
    }
}
