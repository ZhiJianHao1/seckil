package com.zhi.seckill.infrastructure.repository;

import com.zhi.seckill.domain.model.SeckillUser;
import com.zhi.seckill.domain.repository.SeckillUserRepository;
import com.zhi.seckill.infrastructure.mapper.SeckillUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZhiJH
 * @description 用户
 * @date 2024/5/11
 */
@Component
public class SeckillUserRepositoryImpl implements SeckillUserRepository {

    @Autowired
    private SeckillUserMapper seckillUserMapper;

    @Override
    public SeckillUser getSeckillUserByUserName(String userName) {
        return seckillUserMapper.getSeckillUserByUserName(userName);
    }
}
