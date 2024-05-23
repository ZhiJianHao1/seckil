package com.zhi.seckill.application.service.impl;

import com.zhi.seckill.application.builder.SeckillActivityBuilder;
import com.zhi.seckill.application.cache.model.SeckillBusinessCache;
import com.zhi.seckill.application.cache.service.activity.SeckillActivityCacheService;
import com.zhi.seckill.application.cache.service.activity.SeckillActivityListCacheService;
import com.zhi.seckill.application.common.SeckillActivityCommand;
import com.zhi.seckill.application.service.SeckillActivityService;
import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.model.dto.SeckillActivityDTO;
import com.zhi.seckill.domain.model.enums.SeckillActivityStatus;
import com.zhi.seckill.domain.exception.SeckillException;
import com.zhi.seckill.domain.model.entity.SeckillActivity;
import com.zhi.seckill.domain.repository.SeckillActivityRepository;
import com.zhi.seckill.infrastructure.utils.beans.BeanUtil;
import com.zhi.seckill.infrastructure.utils.id.SnowFlakeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhiJH
 * @description 秒杀活动
 * @date 2024/5/15
 */
@Service
public class SeckillActivityServiceImpl implements SeckillActivityService {

    @Autowired
    private SeckillActivityRepository seckillActivityRepository;

    @Autowired
    private SeckillActivityListCacheService seckillActivityListCacheService;

    @Autowired
    private SeckillActivityCacheService seckillActivityCacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSeckillActivityDTO(SeckillActivityCommand seckillActivityCommand) {
        if (seckillActivityCommand == null) {
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        SeckillActivity seckillActivity = SeckillActivityBuilder.toSeckillActivity(seckillActivityCommand);
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

    @Override
    public List<SeckillActivityDTO> getSeckillActivityList(Integer status, Long version) {
        SeckillBusinessCache<List<SeckillActivity>> seckillActivityListCache = seckillActivityListCacheService.getCacheActivities(status, version);
        if (!seckillActivityListCache.isExist()) {
            throw new SeckillException(HttpCode.ACTIVITY_NOT_EXISTS);
        }
        if (seckillActivityListCache.isRetryLater()) {
            throw new SeckillException(HttpCode.RETRY_LATER);
        }
        return seckillActivityListCache.getData().stream().map((seckillActivity) -> {
            SeckillActivityDTO seckillActivityDTO = new SeckillActivityDTO();
            BeanUtil.copyProperties(seckillActivity, seckillActivityDTO);
            seckillActivityDTO.setVersion(seckillActivityListCache.getVersion());
            return seckillActivityDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public SeckillActivityDTO getSeckillActivity(Long id, Long version) {
        if (id == null) {
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        SeckillBusinessCache<SeckillActivity> seckillActivityCache = seckillActivityCacheService.getCachedSeckillActivity(id, version);
        //
        if (!seckillActivityCache.isExist()) {
            throw new SeckillException(HttpCode.ACTIVITY_NOT_EXISTS);
        }
        if (seckillActivityCache.isRetryLater()) {
            throw new SeckillException(HttpCode.RETRY_LATER);
        }
        SeckillActivityDTO seckillActivityDTO = SeckillActivityBuilder.toSeckillActivityDTO(seckillActivityCache.getData());
        seckillActivityDTO.setVersion(seckillActivityCache.getVersion());
        return seckillActivityDTO;
    }
}
