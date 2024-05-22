package com.zhi.seckill.domain.repository;

import com.zhi.seckill.domain.model.entity.SeckillUser;

/**
 * @author ZhiJH
 * @description 用户
 * @date 2024/5/11
 */
public interface SeckillUserRepository {
    /**
     * 根据用户名获取用户信息
     * @param userName 用户名称
     * @return 用户信息
     */
    SeckillUser getSeckillUserByUserName(String userName);
}
