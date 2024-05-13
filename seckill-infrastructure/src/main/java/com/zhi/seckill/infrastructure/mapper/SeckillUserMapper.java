package com.zhi.seckill.infrastructure.mapper;

import com.zhi.seckill.domain.model.SeckillUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZhiJH
 * @description 用户
 * @date 2024/5/11
 */
public interface SeckillUserMapper {
    /**
     * 根据用户名获取用户信息
     */
    SeckillUser getSeckillUserByUserName(@Param("userName") String userName);
}
