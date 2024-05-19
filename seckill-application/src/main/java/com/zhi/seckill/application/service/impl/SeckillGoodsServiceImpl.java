package com.zhi.seckill.application.service.impl;

import com.zhi.seckill.application.service.SeckillGoodsService;
import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.dto.SeckillGoodsDTO;
import com.zhi.seckill.domain.enums.SeckillGoodsStatus;
import com.zhi.seckill.domain.exception.SeckillException;
import com.zhi.seckill.domain.model.SeckillActivity;
import com.zhi.seckill.domain.model.SeckillGoods;
import com.zhi.seckill.domain.repository.SeckillActivityRepository;
import com.zhi.seckill.domain.repository.SeckillGoodsRepository;
import com.zhi.seckill.infrastructure.utils.beans.BeanUtil;
import com.zhi.seckill.infrastructure.utils.id.SnowFlakeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ZhiJH
 * @description 商品信息
 * @date 2024/5/18
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsRepository seckillGoodsRepository;

    @Autowired
    private SeckillActivityRepository seckillActivityRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveSeckillGoods(SeckillGoodsDTO seckillGoodsDTO) {
        if (seckillGoodsDTO == null) {
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        SeckillActivity seckillActivity = seckillActivityRepository.getSeckillActivityById(seckillGoodsDTO.getActivityId());
        if (seckillActivity == null) {
            throw new SeckillException(HttpCode.ACTIVITY_NOT_EXISTS);
        }
        SeckillGoods seckillGoods = new SeckillGoods();
        BeanUtil.copyProperties(seckillGoodsDTO, seckillGoods);
        seckillGoods.setStartTime(seckillActivity.getStartTime());
        seckillGoods.setEndTime(seckillActivity.getEndTime());
        seckillGoods.setAvailableStock(seckillGoodsDTO.getInitialStock());
        seckillGoods.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
        seckillGoods.setStatus(SeckillGoodsStatus.PUBLISHED.getCode());
        return seckillGoodsRepository.saveSeckillGoods(seckillGoods);
    }

    @Override
    public SeckillGoods getSeckillGoodsId(Long id) {
        return seckillGoodsRepository.getSeckillGoodsById(id);
    }

    @Override
    public List<SeckillGoods> getSeckillGoodsByActivityId(Long activityId) {
        return seckillGoodsRepository.getSeckillGoodsByActivityId(activityId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(Integer status, Long id) {
        return seckillGoodsRepository.updateStatus(status, id);
    }

    @Override
    public int updateAvailableStock(Integer count, Long id) {
        return seckillGoodsRepository.updateAvailableStock(count, id);
    }

    @Override
    public Integer getAvailableStockById(Long id) {
        return seckillGoodsRepository.getAvailableStockById(id);
    }
}
