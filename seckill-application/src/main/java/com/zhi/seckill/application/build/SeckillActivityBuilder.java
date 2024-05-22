package com.zhi.seckill.application.build;

import com.zhi.seckill.application.common.SeckillActivityCommand;
import com.zhi.seckill.domain.model.dto.SeckillActivityDTO;
import com.zhi.seckill.domain.model.entity.SeckillActivity;
import com.zhi.seckill.infrastructure.utils.beans.BeanUtil;

/**
 * @author ZhiJH
 * @description 秒杀活动构建类
 * @date 2024/5/22
 */
public class SeckillActivityBuilder {
    public static SeckillActivity toSeckillActivity(SeckillActivityCommand seckillActivityCommand) {
        if (seckillActivityCommand == null) {
            return null;
        }
        SeckillActivity seckillActivity = new SeckillActivity();
        BeanUtil.copyProperties(seckillActivityCommand, seckillActivity);
        return seckillActivity;
    }

    public static SeckillActivityDTO toSeckillActivityDTO(SeckillActivity seckillActivity) {
        if (seckillActivity == null) {
            return null;
        }
        SeckillActivityDTO seckillActivityDTO = new SeckillActivityDTO();
        BeanUtil.copyProperties(seckillActivity, seckillActivityDTO);
        return seckillActivityDTO;
    }
}
