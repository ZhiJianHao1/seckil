package com.zhi.seckill.application.service.impl;

import com.zhi.seckill.application.service.RedisService;
import com.zhi.seckill.application.service.SeckillUserService;
import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.constants.SeckillConstants;
import com.zhi.seckill.domain.exception.SeckillException;
import com.zhi.seckill.domain.model.SeckillUser;
import com.zhi.seckill.domain.repository.SeckillUserRepository;
import com.zhi.seckill.infrastructure.shiro.utils.CommonsUtils;
import com.zhi.seckill.infrastructure.shiro.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhiJH
 * @description
 * @date 2024/5/12
 */
@Service
public class SeckillUserServiceImpl implements SeckillUserService {

    @Autowired
    private SeckillUserRepository seckillUserRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public SeckillUser getSeckillUserByUserName(String userName) {
        return seckillUserRepository.getSeckillUserByUserName(userName);
    }

    @Override
    public String login(String userName, String password) {
        if (StringUtils.isEmpty(userName)) {
            throw new SeckillException(HttpCode.USERNAME_IS_NULL);
        }
        if (StringUtils.isEmpty(password)) {
            throw new SeckillException(HttpCode.PASSWORD_IS_NULL);
        }
        SeckillUser seckillUser = seckillUserRepository.getSeckillUserByUserName(userName);
        if (seckillUser == null) {
            throw new SeckillException(HttpCode.USERNAME_IS_ERROR);
        }
        String paramsPassword = CommonsUtils.encryptPassword(password, userName);
        if (!paramsPassword.equals(seckillUser.getPassword())) {
            throw new SeckillException(HttpCode.PASSWORD_IS_ERROR);
        }
        String token = JwtUtils.sign(seckillUser.getId());
        String key = SeckillConstants.getKey(SeckillConstants.USER_KEY_PREFIX, String.valueOf(seckillUser.getId()));
        // 缓存到Redis
        redisService.set(key, token);
        // 返回token
        return token;
    }
}
