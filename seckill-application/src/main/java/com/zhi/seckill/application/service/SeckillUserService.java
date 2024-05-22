package com.zhi.seckill.application.service;

import com.zhi.seckill.domain.model.entity.SeckillUser;

/**
 * @author ZhiJH
 * @description 用户
 * @date 2024/5/12
 */
public interface SeckillUserService {
    /**
     * 根据用户名获取用户信息
     */
    SeckillUser getSeckillUserByUserName(String userName);

    /**
     * 根据用户Id获取用户信息
     */
    SeckillUser getSeckillUserByUserId(Long userId);

    /**
     * 登录
     */
    String login(String userName, String password);
}
